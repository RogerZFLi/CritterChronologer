package com.udacity.jdnd.course3.critter.entity;


import com.sun.istack.NotNull;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Nationalized
    private String name;
    @Column(name = "address_full")
    @Nationalized
    private String address;
    @Column(name = "contact_number")
    private String phoneNumber;


    public User() {
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
