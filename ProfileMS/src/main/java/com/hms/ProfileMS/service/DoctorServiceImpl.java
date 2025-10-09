package com.hms.ProfileMS.service;

import com.hms.ProfileMS.dto.DoctorDTO;
import com.hms.ProfileMS.dto.DoctorDropdown;
import com.hms.ProfileMS.entity.Doctor;
import com.hms.ProfileMS.entity.Patient;
import com.hms.ProfileMS.exception.ErrorCode;
import com.hms.ProfileMS.exception.HmsException;
import com.hms.ProfileMS.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;


    @Override
    public Long addDoctor(DoctorDTO doctorDTO) {
        if (doctorDTO.getEmail() != null && doctorRepository.findByEmail(doctorDTO.getEmail()).isPresent()) {
            throw new HmsException(ErrorCode.DOCTOR_ALREADY_EXISTS);
        }
        if (doctorDTO.getLicenseNo() != null && doctorRepository.findByLicenseNo(doctorDTO.getLicenseNo()).isPresent()) {
            throw new HmsException(ErrorCode.DOCTOR_ALREADY_EXISTS);
        }
        return doctorRepository.save(doctorDTO.toEntity()).getId();
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new HmsException(ErrorCode.DOCTOR_NOT_FOUND)).toDTO();
    }

    @Override
    public DoctorDTO updatePatient(DoctorDTO doctorDTO) {
        doctorRepository.findById(doctorDTO.getId())
                .orElseThrow(() -> new HmsException(ErrorCode.DOCTOR_NOT_FOUND));
        return doctorRepository.save(doctorDTO.toEntity()).toDTO();
    }

    @Override
    public Boolean doctorExists(Long id) {
        return doctorRepository.existsById(id);
    }

    @Override
    public List<DoctorDropdown> getDoctorDropdowns() {
        return doctorRepository.findAllDoctorDropdowns();
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(Doctor::toDTO).toList();
    }

    @Override
    public List<DoctorDropdown> getDoctorsById(List<Long> ids) {
        return doctorRepository.findAllDoctorDropdownsByIds(ids);
    }


}
