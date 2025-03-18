package com.example.pet_adoption.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "pets")
public class Pet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String animal;
    
    @NotBlank
    private String name;

    private String breed;
    private Date dateOfBirth;
    private Boolean adoptionStatus = false;


    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MedicalRecord> medicalRecords = new ArrayList<>();
    
    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PetStory petStory;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AdoptionApplication> adoptionApplications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
    
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getAdoptionStatus() {
        return adoptionStatus;
    }

    public void setAdoptionStatus(Boolean adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecords.add(medicalRecord);
        medicalRecord.setPet(this);
    }

    public PetStory getPetStory() {
        return petStory;
    }

    public void setPetStory(PetStory petStory) {
        this.petStory = petStory;
    }

    public List<AdoptionApplication> getAdoptionApplications() {
        return adoptionApplications;
    }

    public void setAdoptionApplications(List<AdoptionApplication> adoptionApplications) {
        this.adoptionApplications = adoptionApplications;
    }
}
