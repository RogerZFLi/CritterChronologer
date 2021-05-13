package com.udacity.jdnd.course3.critter.entity;


import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Employee extends User{
    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "employee_skill",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    @Column(name = "skill")
    private Set<EmployeeSkill> skills;
    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "employee_availableDay",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    @Column(name = "day")
    private Set<DayOfWeek> daysAvailable;

    public Employee() {
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public void addSkill(EmployeeSkill skill) { this.skills.add(skill);}

    public void addAvailableDay(DayOfWeek day) { this.daysAvailable.add( day);}
}

