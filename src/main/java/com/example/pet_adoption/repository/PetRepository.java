package com.example.pet_adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pet_adoption.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

    
}
