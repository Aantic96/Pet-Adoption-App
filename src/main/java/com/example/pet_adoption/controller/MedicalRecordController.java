package com.example.pet_adoption.controller;

import java.util.List;
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

import com.example.pet_adoption.model.MedicalRecord;
import com.example.pet_adoption.service.MedicalRecordService;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {
    
    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord createdMedicalRecord = medicalRecordService.createMedicalRecord(medicalRecord);
        return new ResponseEntity<>(createdMedicalRecord, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MedicalRecord>> getAllMedicalRecords(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size)
    {
        Page<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords(page, size);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable Long id) {
        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordById(id);
        if (medicalRecord.isPresent()) {
            return new ResponseEntity<>(medicalRecord.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord updatedMedicalRecord) {
        MedicalRecord medicalRecord = medicalRecordService.updateMedicalRecord(id, updatedMedicalRecord);
        if (medicalRecord != null) {
            return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        boolean isDeleted = medicalRecordService.deleteMedicalRecord(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
