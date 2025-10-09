package com.hms.appointment.Appointment.service;

import com.hms.appointment.Appointment.clients.ProfileClient;
import com.hms.appointment.Appointment.dto.*;
import com.hms.appointment.Appointment.entity.Appointment;
import com.hms.appointment.Appointment.exception.ErrorCode;
import com.hms.appointment.Appointment.exception.HmsException;
import com.hms.appointment.Appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentRepository appointmentRepository;
    private final ApiService apiService;
    private final ProfileClient profileClient;

    @Override
    public Long scheduleAppointment(AppointmentDTO appointmentDTO) {
        Boolean doctorExists = profileClient.doctorExists(appointmentDTO.getDoctorId());
        if (doctorExists == null || !doctorExists) {
            throw new HmsException(ErrorCode.DOCTOR_NOT_FOUND);
        }
        Boolean patientExists = profileClient.patientExists(appointmentDTO.getPatientId());
        if (patientExists == null || !patientExists) {
            throw new HmsException(ErrorCode.PATIENT_NOT_FOUND);
        }
        appointmentDTO.setStatus(Status.SCHEDULED);
        return appointmentRepository.save(appointmentDTO.toEntity()).getId();
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new HmsException(ErrorCode.APPOINTMENT_NOT_FOUND));
        if (appointment.getStatus().equals(Status.CANCELLED)) {
            throw new HmsException(ErrorCode.APPOINTMENT_ALREADY_CANCELLED);
        }
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    public void completeAppointment(Long appointmentId) {
//        Appointment appointment = appointmentRepository.findById(appointmentId)
//                .orElseThrow(() -> new HmsException(ErrorCode.APPOINTMENT_NOT_FOUND));
//        if (appointment.getStatus().equals(Status.CANCELLED)) {
//            throw new HmsException(ErrorCode.APPOINTMENT_ALREADY_CANCELLED);
//        }
//        appointment.setStatus(Status.CANCELLED);
//        appointmentRepository.save(appointment);
    }

    @Override
    public void rescheduleAppointment(Long appointmentId, String newDateTime) {

    }

    @Override
    public AppointmentDTO getAppointmentDetails(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new HmsException(ErrorCode.APPOINTMENT_NOT_FOUND)).toDTO();
    }

    @Override
    public AppointmentDetails getAppointmentDetailsWithName(Long appointmentId) {
        AppointmentDTO appointmentDTO = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new HmsException(ErrorCode.APPOINTMENT_NOT_FOUND)).toDTO();
        DoctorDTO doctorDTO = profileClient.getDoctorById(appointmentDTO.getDoctorId());
        PatientDTO patientDTO = profileClient.getPatientById(appointmentDTO.getPatientId());
        return AppointmentDetails.builder()
                .id(appointmentDTO.getId())
                .patientId(appointmentDTO.getPatientId())
                .patientName(patientDTO.getName())
                .doctorId(appointmentDTO.getDoctorId())
                .doctorName(doctorDTO.getName())
                .appointmentTime(appointmentDTO.getAppointmentTime())
                .status(appointmentDTO.getStatus())
                .reason(appointmentDTO.getReason())
                .notes(appointmentDTO.getNotes())
                .patientEmail(patientDTO.getEmail())
                .patientPhone(patientDTO.getPhone()).build();
    }

    @Override
    public List<AppointmentDetails> getAllAppointmentDetailsByPatientId(Long patientId) {
        return appointmentRepository.findAllByPatientId(patientId).stream()
                .map(appointment -> {
                    DoctorDTO doctorDTO =
                            profileClient.getDoctorById(appointment.getDoctorId());
                    appointment.setDoctorName(doctorDTO.getName());
                    return appointment;
                }).toList();
    }

    @Override
    public List<AppointmentDetails> getAllAppointmentDetailsByDoctorId(Long doctorId) {
        return appointmentRepository.findAllByDoctorId(doctorId).stream()
                .map(appointment -> {
                    PatientDTO patientDTO =
                            profileClient.getPatientById(appointment.getPatientId());
                    appointment.setPatientName(patientDTO.getName());
                    appointment.setPatientEmail(patientDTO.getEmail());
                    appointment.setPatientPhone(patientDTO.getPhone());
                    return appointment;
                }).toList();
    }
}
