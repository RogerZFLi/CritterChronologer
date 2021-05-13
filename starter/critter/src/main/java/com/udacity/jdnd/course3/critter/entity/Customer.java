package com.udacity.jdnd.course3.critter.entity;


import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Customer extends User{

    @Nationalized
    private String notes;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Pet> pets;

    public Customer() {
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void addPet(Pet pet) {
        if (pets == null)
            pets = new ArrayList<>();
        this.pets.add(pet);
    }
}
