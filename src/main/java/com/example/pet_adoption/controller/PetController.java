package com.example.pet_adoption.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pet_adoption.model.MedicalRecord;
import com.example.pet_adoption.model.Pet;
import com.example.pet_adoption.repository.PetRepository;
import com.example.pet_adoption.service.MedicalRecordService;
import com.example.pet_adoption.service.PetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pets")
public class PetController {
    
    @Autowired
    PetService petService;

    @Autowired
    PetRepository petRepository;

    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping
    @ResponseBody
    public List<Pet> getPets(
        @RequestParam(required = false) Boolean status,
        @RequestParam(required = false, name = "dob-after") String dobAfter,
        @RequestParam(required = false) String animal,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10", name = "per-page") int perPage
    ) {
        return petService.findFilteredPets(status, dobAfter, animal, page, perPage); 
    }

    @PostMapping
    public Pet createPet(@Valid @RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pet> patchPet(@PathVariable Long id, @RequestBody Pet updatedPet) {
        Pet pet = petService.patchPet(id, updatedPet);
        if (pet != null) {
            return ResponseEntity.ok(pet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        boolean isDeleted = petService.deletePet(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{petId}/medical-records")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecordsByPet(@PathVariable Long petId) {
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPet(petId);
        
        if (medicalRecords.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }
}
