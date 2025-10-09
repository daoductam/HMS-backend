package com.hms.ProfileMS.api;

import com.hms.ProfileMS.dto.DoctorDropdown;
import com.hms.ProfileMS.dto.PatientDTO;
import com.hms.ProfileMS.service.PatientService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/patient")
@CrossOrigin
@Validated
@RequiredArgsConstructor
public class PatientAPI {

    private final PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<Long> addUser(@RequestBody PatientDTO patientDTO)  {
        return new ResponseEntity<>(patientService.addPatient(patientDTO),
                HttpStatus.CREATED);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id){
        return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);

    }

    @PutMapping("/update")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO){
        return new ResponseEntity<>(patientService.updatePatient(patientDTO), HttpStatus.OK);

    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> patientExists(@PathVariable Long id){
        return new ResponseEntity<>(patientService.patientExists(id), HttpStatus.OK);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return new ResponseEntity<>(patientService.getAllPatients(),
                HttpStatus.OK);
    }

    @GetMapping("/getPatientsById")
    public ResponseEntity<List<DoctorDropdown>> getPatientsById(@RequestParam List<Long> ids) {
        return new ResponseEntity<>(patientService.getPatientsById(ids),
                HttpStatus.OK);
    }
}
