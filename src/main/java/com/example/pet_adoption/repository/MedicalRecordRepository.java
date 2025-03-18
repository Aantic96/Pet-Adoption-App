package com.example.pet_adoption.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pet_adoption.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>{
    List<MedicalRecord> findByPetId(Long petId);
}
