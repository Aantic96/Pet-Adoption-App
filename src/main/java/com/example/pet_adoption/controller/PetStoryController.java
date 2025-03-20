package com.example.pet_adoption.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pet_adoption.model.PetStory;
import com.example.pet_adoption.service.PetStoryService;

@RestController
@RequestMapping("/pet-stories")
public class PetStoryController {
    
    @Autowired
    private PetStoryService petStoryService;

    @PostMapping
    public ResponseEntity<PetStory> createPetStory(@RequestBody PetStory petStory) {
        PetStory createdPetStory = petStoryService.createPetStory(petStory);
        return new ResponseEntity<>(createdPetStory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PetStory>> getAllPetStories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10", name = "per-page") int perPage)
    {
        Page<PetStory> petStories = petStoryService.getAllPetStories(page, perPage);
        return new ResponseEntity<>(petStories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetStory> getPetStoryById(@PathVariable Long id) {
        Optional<PetStory> petStory = petStoryService.getPetStoryById(id);
        return petStory.map(story -> new ResponseEntity<>(story, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetStory> updatePetStory(@PathVariable Long id, @RequestBody PetStory updatedPetStory) {
        PetStory petStory = petStoryService.updatePetStory(id, updatedPetStory);
        return petStory != null 
            ? new ResponseEntity<>(petStory, HttpStatus.OK) 
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetStory(@PathVariable Long id) {
        boolean isDeleted = petStoryService.deletePetStory(id);
        return isDeleted 
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}