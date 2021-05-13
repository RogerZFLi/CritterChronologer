package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;


    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleForPet(Pet pet) {
        return scheduleRepository.findScheduleByPets(pet);
    }

    public List<Schedule> getSchedulesByPet(List<Pet> pets) {
        return scheduleRepository.findByPetsIn(pets);
    }

    public List<Schedule> findScheduleForEmployee(Employee employee) { return scheduleRepository.findScheduleByEmployees(employee);
    }
}
