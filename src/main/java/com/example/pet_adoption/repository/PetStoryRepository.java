package com.example.pet_adoption.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pet_adoption.model.PetStory;

public interface PetStoryRepository extends JpaRepository<PetStory, Long> {
    
}
