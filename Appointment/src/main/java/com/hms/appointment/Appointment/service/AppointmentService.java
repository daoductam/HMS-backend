package com.hms.appointment.Appointment.service;

import com.hms.appointment.Appointment.dto.AppointmentDTO;
import com.hms.appointment.Appointment.dto.AppointmentDetails;

import java.util.List;

public interface AppointmentService {
    Long scheduleAppointment(AppointmentDTO appointmentDTO);
    void cancelAppointment(Long appointmentId);
    void completeAppointment(Long appointmentId);
    void rescheduleAppointment(Long appointmentId, String newDateTime);
    AppointmentDTO getAppointmentDetails(Long appointmentId);
    AppointmentDetails getAppointmentDetailsWithName(Long appointmentId);
    List<AppointmentDetails> getAllAppointmentDetailsByPatientId(Long patientId);
    List<AppointmentDetails> getAllAppointmentDetailsByDoctorId(Long doctorId);
}
