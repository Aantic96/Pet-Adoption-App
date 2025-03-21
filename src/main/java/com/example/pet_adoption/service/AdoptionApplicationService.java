package com.example.pet_adoption.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.example.pet_adoption.model.AdoptionApplication;
import com.example.pet_adoption.model.Pet;
import com.example.pet_adoption.repository.AdoptionApplicationRepository;
import com.example.pet_adoption.repository.PetRepository;

@Service
public class AdoptionApplicationService {
    
    @Autowired
    private AdoptionApplicationRepository adoptionApplicationRepository;

    @Autowired
    private PetRepository petRepository;

    public AdoptionApplication createAdoptionApplication(AdoptionApplication application) {
        if (application.getPet() != null && application.getPet().getId() != null) {
            Pet existingPet = petRepository.findById(application.getPet().getId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

            application.setPet(existingPet);
        } else {
            throw new RuntimeException("AdoptionApplication must be associated with a valid pet.");
        }

        return adoptionApplicationRepository.save(application);
    }

    public Page<AdoptionApplication> getAllApplications(int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        return adoptionApplicationRepository.findAll(pageable);
    }

    public Optional<AdoptionApplication> getApplicationById(Long id) {
        return adoptionApplicationRepository.findById(id);
    }

    public AdoptionApplication updateApplication(Long id, AdoptionApplication updatedApplication) {
        if (adoptionApplicationRepository.existsById(id)) {
            updatedApplication.setId(id);
            return adoptionApplicationRepository.save(updatedApplication);
        }
        return null;
    }

    public boolean deleteApplication(Long id) {
        if (adoptionApplicationRepository.existsById(id)) {
            adoptionApplicationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<AdoptionApplication> getApplicationsByPet(Long petId) {
        return adoptionApplicationRepository.findByPetId(petId);
    }
}
