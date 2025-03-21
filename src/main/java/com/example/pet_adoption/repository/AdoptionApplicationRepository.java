package com.example.pet_adoption.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pet_adoption.model.AdoptionApplication;

public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Long> {
    List<AdoptionApplication> findByPetId(Long petId);

    List<AdoptionApplication> findByPetIdAndApplicantContactInfo(Long petId, String applicantContactInfo);
}
