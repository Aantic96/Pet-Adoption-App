package com.example.pet_adoption.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pet_adoption.model.Pet;
import com.example.pet_adoption.repository.PetRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pets")
public class PetController {
    
    @Autowired
    PetRepository petRepository;

    @GetMapping
    public List<Pet> getPets(
        @RequestParam(required = false) Boolean status,
        @RequestParam(required = false) Date dobAfter,
        @RequestParam(required = false) String animal
    ) {
        System.out.println("Filters received - Status: " + status + ", DOB After: " + dobAfter + ", Animal: " + animal);

        return petRepository.findFilteredPets(status, dobAfter, animal); 
    }

    @PostMapping
    public Pet createPet(@Valid @RequestBody Pet pet) {
        return petRepository.save(pet);
    }
}
