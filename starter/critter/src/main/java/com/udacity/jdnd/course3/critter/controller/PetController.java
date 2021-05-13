package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet pet = convertPetDTOToPet(petDTO);
        Customer owner = userService.getCustomerById(petDTO.getOwnerId());
        pet.setCustomer(owner);
        pet = petService.savePet(pet);
        owner.addPet(pet);
        userService.saveCustomer(owner);
        PetDTO returnedPetDTO = convertPetToPetDTO(pet);
        returnedPetDTO.setOwnerId(owner.getId());
        return returnedPetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        PetDTO petDTO = convertPetToPetDTO(pet);
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(p->{
            PetDTO petDTO = convertPetToPetDTO(p);
            return petDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.findPetsByOwner(userService.getCustomerById(ownerId));
        return pets.stream().map(p->{
            PetDTO petDTO = convertPetToPetDTO(p);
            return petDTO;
        }).collect(Collectors.toList());
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = null;
        if ( pet != null ) {
            petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
