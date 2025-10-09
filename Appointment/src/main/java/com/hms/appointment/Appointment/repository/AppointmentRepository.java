package com.hms.appointment.Appointment.repository;

import com.hms.appointment.Appointment.dto.AppointmentDetails;
import com.hms.appointment.Appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT new com.hms.appointment.Appointment.dto.AppointmentDetails(" +
            "a.id, a.patientId, null, null, null, " +
            "a.doctorId, null, a.appointmentTime, a.status, a.reason, a.notes) " +
            "FROM Appointment a WHERE a.patientId = :patientId")
    List<AppointmentDetails> findAllByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT new com.hms.appointment.Appointment.dto.AppointmentDetails(" +
            "a.id, a.patientId, null, null, null, " +
            "a.doctorId, null, a.appointmentTime, a.status, a.reason, a.notes) " +
            "FROM Appointment a WHERE a.doctorId = :doctorId")
    List<AppointmentDetails> findAllByDoctorId(@Param("doctorId") Long doctorId);

}
