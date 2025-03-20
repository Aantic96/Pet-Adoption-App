package com.example.pet_adoption.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pet_adoption.model.Pet;
import com.example.pet_adoption.model.PetStory;
import com.example.pet_adoption.repository.PetRepository;
import com.example.pet_adoption.repository.PetStoryRepository;

@Service
public class PetStoryService {
   
    @Autowired
    private PetStoryRepository petStoryRepository;

    @Autowired
    private PetRepository petRepository;

    public Page<PetStory> getAllPetStories(int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        return petStoryRepository.findAll(pageable);
    }

    public Optional<PetStory> getPetStoryById(Long id) {
        return petStoryRepository.findById(id);
    }

    public PetStory createPetStory(PetStory petStory) {
        if (petStory.getPet() != null && petStory.getPet().getId() != null) {
            Pet existingPet = petRepository.findById(petStory.getPet().getId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

                petStory.setPet(existingPet);
        } else {
            throw new RuntimeException("PetStory must be associated with a valid pet.");
        }

        return petStoryRepository.save(petStory);
    }

    public PetStory updatePetStory(Long id, PetStory updatedPetStory) {
        if (petStoryRepository.existsById(id)) {
            updatedPetStory.setId(id);
            return petStoryRepository.save(updatedPetStory);
        }
        return null;
    }

    public boolean deletePetStory(Long id) {
        if (petStoryRepository.existsById(id)) {
            petStoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
