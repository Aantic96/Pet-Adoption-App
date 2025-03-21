package com.example.pet_adoption.event_listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.pet_adoption.event.AdoptionApplicationEvent;
import com.example.pet_adoption.miscellaneous.AdoptionStatus.AdoptionApplicationStatus;
import com.example.pet_adoption.model.AdoptionApplication;
import com.example.pet_adoption.model.Pet;
import com.example.pet_adoption.repository.AdoptionApplicationRepository;
import com.example.pet_adoption.repository.PetRepository;

@Component
public class AdoptionApplicationEventListener {

    @Autowired
    private AdoptionApplicationRepository adoptionApplicationRepository;

    @Autowired
    private PetRepository petRepository;

    @EventListener
    public void handleAdoptionApplicationEvent(AdoptionApplicationEvent event) {

        Long petId = event.getPetId();
        Long acceptedAppId = event.getAdoptedApplicationId();
    
        List<AdoptionApplication> applications = adoptionApplicationRepository.findByPetId(petId);
        for (AdoptionApplication application : applications) {
            if(!application.getId().equals(acceptedAppId)) {
                application.setStatus(AdoptionApplicationStatus.REJECTED);
                adoptionApplicationRepository.save(application);
            }
        }

        Pet pet = petRepository.findById(petId).orElse(null);
        if(pet != null) {
            pet.setAdoptionStatus(true);
            petRepository.save(pet);
        }
    }
}
