package com.example.pet_adoption.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pet_adoption.model.MedicalRecord;
import com.example.pet_adoption.model.Pet;
import com.example.pet_adoption.repository.MedicalRecordRepository;
import com.example.pet_adoption.repository.PetRepository;

@Service
public class MedicalRecordService {
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PetRepository petRepository;

    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecord.getPet() != null && medicalRecord.getPet().getId() != null) {
            Pet existingPet = petRepository.findById(medicalRecord.getPet().getId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

            medicalRecord.setPet(existingPet);
        } else {
            throw new RuntimeException("MedicalRecord must be associated with a valid pet.");
        }

        return medicalRecordRepository.save(medicalRecord);
    }

    public Page<MedicalRecord> getAllMedicalRecords(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return medicalRecordRepository.findAll(pageable);
    }

    public Optional<MedicalRecord> getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id);
    }

    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord updatedMedicalRecord) {
        if (medicalRecordRepository.existsById(id)) {
            updatedMedicalRecord.setId(id); 
            return medicalRecordRepository.save(updatedMedicalRecord);
        }
        return null;
    }

    public boolean deleteMedicalRecord(Long id) {
        if (medicalRecordRepository.existsById(id)) {
            medicalRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<MedicalRecord> getMedicalRecordsByPet(Long petId) {
        return medicalRecordRepository.findByPetId(petId);
    }
}
