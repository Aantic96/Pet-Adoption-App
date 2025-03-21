package com.example.pet_adoption.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pet_adoption.model.AdoptionApplication;
import com.example.pet_adoption.service.AdoptionApplicationService;

@RestController
@RequestMapping("/adoption-applications")
public class AdoptionApplicationController {
    
    @Autowired
    AdoptionApplicationService adoptionApplicationService;

    @PostMapping
    public ResponseEntity<AdoptionApplication> createApplication(@RequestBody AdoptionApplication application) {
        AdoptionApplication createdApplication = adoptionApplicationService.createAdoptionApplication(application);
        return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AdoptionApplication>> getAllApplications(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10", name = "per-page") int perPage) 
    {
        Page<AdoptionApplication> applications = adoptionApplicationService.getAllApplications(page, perPage);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdoptionApplication> getApplicationById(@PathVariable Long id) {
        Optional<AdoptionApplication> application = adoptionApplicationService.getApplicationById(id);
        return application.map(app -> new ResponseEntity<>(app, HttpStatus.OK))
                          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdoptionApplication> updateApplication(@PathVariable Long id, @RequestBody AdoptionApplication updatedApplication) {
        AdoptionApplication application = adoptionApplicationService.updateApplication(id, updatedApplication);
        return application != null 
            ? new ResponseEntity<>(application, HttpStatus.OK) 
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        boolean isDeleted = adoptionApplicationService.deleteApplication(id);
        return isDeleted 
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
