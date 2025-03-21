package com.example.pet_adoption.event;

import org.springframework.context.ApplicationEvent;

public class AdoptionApplicationEvent extends ApplicationEvent {
    
    private final Long adoptedApplicationId;
    private final Long petId;

    public AdoptionApplicationEvent(Object source, Long adoptedApplicationId, Long petId) {
        super(source);
        this.adoptedApplicationId = adoptedApplicationId;
        this.petId = petId;
    }

    public Long getAdoptedApplicationId() {
        return adoptedApplicationId;
    }

    public Long getPetId() {
        return petId;
    }
}
