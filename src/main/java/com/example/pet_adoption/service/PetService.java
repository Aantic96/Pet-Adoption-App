package com.example.pet_adoption.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pet_adoption.model.Pet;
import com.example.pet_adoption.repository.PetRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;
    
    @Autowired
    StringToDateConverter dateConverter;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Pet> findFilteredPets(Boolean status, String dobAfter, String animal, int page, int perPage) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> query = cb.createQuery(Pet.class);
        Root<Pet> petRoot = query.from(Pet.class);

        List<Predicate> predicates = new ArrayList<>();

        if (status != null) {
            predicates.add(cb.equal(petRoot.get("adoptionStatus"), status));
        }

        if (dobAfter != null) {
            Date dobAfterConverted = dateConverter.convertStringToDate(dobAfter);
            predicates.add(cb.greaterThan(petRoot.get("dateOfBirth"), dobAfterConverted));
        }

        if (animal != null) {
            predicates.add(cb.like(petRoot.get("animal"), "%" + animal + "%"));
        }
        
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Pet> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(page * perPage);
        typedQuery.setMaxResults(perPage);

        return typedQuery.getResultList();

    }

    public Pet patchPet(Long id, Pet updatedPet) {
        Pet existingPet = petRepository.findById(id).orElse(null);
        if (existingPet != null) {

            if(updatedPet.getName() != null) {
                existingPet.setName(updatedPet.getName());
            }
            if (updatedPet.getAdoptionStatus() != null) {
                existingPet.setAdoptionStatus(updatedPet.getAdoptionStatus());
            }
            if (updatedPet.getDateOfBirth() != null) {
                existingPet.setDateOfBirth(updatedPet.getDateOfBirth());
            }
            if (updatedPet.getAnimal() != null) {
                existingPet.setAnimal(updatedPet.getAnimal());
            }
            if (updatedPet.getBreed() != null) {
                existingPet.setBreed(updatedPet.getBreed());
            }

            return petRepository.save(existingPet);
        }

        return null;
    }

    public boolean deletePet(Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
