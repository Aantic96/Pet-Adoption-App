package com.example.pet_adoption.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pet_adoption.model.Pet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @PersistenceContext
    EntityManager entityManager = null;

    default List<Pet> findFilteredPets(Boolean status, Date dobAfter, String animal) {

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

        // Add filter for date of birth if it's provided
        if (dobAfter != null) {
            predicates.add(cb.greaterThan(petRoot.get("dateOfBirth"), dobAfter));
        }

        // Add filter for animal type if it's provided
        if (animal != null) {
            predicates.add(cb.like(petRoot.get("animal"), "%" + animal + "%"));  // Using LIKE for string match
        }

        // Apply all the conditions (AND logic) to the query
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // Execute the query and return the list of filtered pets
        return entityManager.createQuery(query).getResultList();
    }
}
