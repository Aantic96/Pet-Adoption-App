package com.example.pet_adoption.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pet_adoption.model.Pet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class PetService {
    
    @Autowired
    StringToDateConverter dateConverter;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Pet> findFilteredPets(Boolean status, String dobAfter, String animal) {

        // Get CriteriaBuilder and CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> query = cb.createQuery(Pet.class);
        Root<Pet> petRoot = query.from(Pet.class);

        // List to hold predicates (conditions)
        List<Predicate> predicates = new ArrayList<>();

        // Add filter for adoption status if it's provided
        if (status != null) {
            predicates.add(cb.equal(petRoot.get("adoptionStatus"), status));
        }

        if (dobAfter != null) {
            Date dobAfterConverted = dateConverter.convertStringToDate(dobAfter);
            predicates.add(cb.greaterThan(petRoot.get("dateOfBirth"), dobAfterConverted));
        }

        // Add filter for animal type if it's provided
        if (animal != null) {
            predicates.add(cb.like(petRoot.get("animal"), "%" + animal + "%"));  // Using LIKE for string match
        }

        // Apply all the conditions (AND logic) to the query
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        System.out.println(query);

        // Execute the query and return the list of filtered pets
        return entityManager.createQuery(query).getResultList();
    }
}
