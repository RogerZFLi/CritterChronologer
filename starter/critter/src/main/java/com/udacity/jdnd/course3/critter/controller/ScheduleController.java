package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PetService petService;
    @Autowired
    UserService userService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().map(id->{
            Employee employee = userService.getEmployeeById(id);
            return  employee;
        }).collect(Collectors.toList()));
        schedule.setPets(scheduleDTO.getPetIds().stream().map(id->{
            Pet pet = petService.getPetById(id);
            return pet;
        }).collect(Collectors.toList()));
        schedule = scheduleService.createSchedule(schedule);
        scheduleDTO.setId(schedule.getId());
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream().map(schedule -> {
            ScheduleDTO scheduleDTO = convertScheduleToScheduleDTO(schedule);
            return scheduleDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        return scheduleService.findScheduleForPet(petService.getPetById(petId)).stream().map(schedule -> {
            ScheduleDTO scheduleDTO = convertScheduleToScheduleDTO(schedule);
            return scheduleDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findScheduleForEmployee(userService.getEmployeeById(employeeId)).stream().map(schedule -> {
            ScheduleDTO scheduleDTO = convertScheduleToScheduleDTO(schedule);

            return scheduleDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = userService.getCustomerById(customerId);
        List<Pet> pets = petService.findPetsByOwner(customer);
        return scheduleService.getSchedulesByPet(pets).stream().map(schedule -> {
            ScheduleDTO scheduleDTO = convertScheduleToScheduleDTO(schedule);
            return scheduleDTO;
        }).collect(Collectors.toList());
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = null;
        if ( schedule != null ) {
            scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(employee -> {
                Long id = employee.getId();
                return id;
            }).collect(Collectors.toList()));
            scheduleDTO.setPetIds(schedule.getPets().stream().map(pet -> {
                Long id = pet.getId();
                return id;
            }).collect(Collectors.toList()));
        }
        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }
}
