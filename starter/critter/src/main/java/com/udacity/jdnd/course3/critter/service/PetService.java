package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPetById(Long petId) {
        return petRepository.findById(petId).get();
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> findPetsByOwner(Customer owner) {
        return petRepository.findPetsByCustomer(owner);
    }
}
