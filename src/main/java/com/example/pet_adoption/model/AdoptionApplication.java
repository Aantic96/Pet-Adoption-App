package com.example.pet_adoption.model;

import com.example.pet_adoption.miscellaneous.AdoptionStatus.AdoptionApplicationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "adoption_applications")
public class AdoptionApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicantName;

    @NotNull(message = "Applicant contact info cannot be null.")
    @Column(nullable = false)
    private String applicantContactInfo;

    @Enumerated(EnumType.STRING)
    private AdoptionApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantContactInfo() {
        return applicantContactInfo;
    }

    public void setApplicantContactInfo(String applicantContactInfo) {
        this.applicantContactInfo = applicantContactInfo;
    }

    public AdoptionApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(AdoptionApplicationStatus status) {
        this.status = status;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}