package com.hms.PharmacyMS.service;

import com.hms.PharmacyMS.dto.MedicineDTO;
import com.hms.PharmacyMS.entity.Medicine;
import com.hms.PharmacyMS.exception.ErrorCode;
import com.hms.PharmacyMS.exception.HmsException;
import com.hms.PharmacyMS.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService{
    private final MedicineRepository medicineRepository;

    @Override
    public Long addMedicine(MedicineDTO medicineDTO) {
        Optional<Medicine> optional = medicineRepository
                .findByNameIgnoreCaseAndDosageIgnoreCase(medicineDTO.getName(), medicineDTO.getDosage());
        if (optional.isPresent()) {
            throw new HmsException(ErrorCode.MEDICINE_ALREADY_EXISTS);

        }
        medicineDTO.setStock(0);
        medicineDTO.setCreatedAt(LocalDateTime.now());
        return medicineRepository.save(medicineDTO.toEntity()).getId();
    }

    @Override
    public MedicineDTO getMedicineById(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new HmsException(ErrorCode.MEDICINE_NOT_FOUND)).toDTO();
    }

    @Override
    public void updateMedicine( MedicineDTO medicineDTO) {
        Medicine existingMedicine =
                medicineRepository.findById(medicineDTO.getId())
                        .orElseThrow(() -> new HmsException(ErrorCode.MEDICINE_NOT_FOUND));
        if (!(medicineDTO.getName().equalsIgnoreCase(existingMedicine.getName())
        && !medicineDTO.getDosage().equalsIgnoreCase(existingMedicine.getDosage()))) {
            Optional<Medicine> optional = medicineRepository
                    .findByNameIgnoreCaseAndDosageIgnoreCase(medicineDTO.getName(), medicineDTO.getDosage());
            if (optional.isPresent()) {
                throw new HmsException(ErrorCode.MEDICINE_ALREADY_EXISTS);

            }

        }
        existingMedicine.setName(medicineDTO.getName());
        existingMedicine.setDosage(medicineDTO.getDosage());
        existingMedicine.setCategory(medicineDTO.getCategory());
        existingMedicine.setType(medicineDTO.getType());
        existingMedicine.setManufacturer(medicineDTO.getManufacturer());
        existingMedicine.setUnitPrice(medicineDTO.getUnitPrice());
        existingMedicine.setCreatedAt(medicineDTO.getCreatedAt());
        medicineRepository.save(existingMedicine);

    }


    @Override
    public List<MedicineDTO> getAllMedicines() {
        return medicineRepository.findAll().stream()
                .map(Medicine::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getStockById(Long id) {
        return Math.toIntExact(medicineRepository.findStockById(id)
                .orElseThrow(() -> new HmsException(ErrorCode.MEDICINE_NOT_FOUND)));
    }

    @Override
    public Integer addStock(Long id, Integer quantity) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new HmsException(ErrorCode.MEDICINE_NOT_FOUND));
        medicine.setStock(medicine.getStock()!=null ? medicine.getStock()+quantity :  quantity);
        medicineRepository.save(medicine);
        return medicine.getStock();
    }

    @Override
    public Integer removeStock(Long id, Integer quantity) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new HmsException(ErrorCode.MEDICINE_NOT_FOUND));
        medicine.setStock(medicine.getStock()!=null ? medicine.getStock()-quantity :  quantity);

        medicineRepository.save(medicine);
        return medicine.getStock();
    }
}
