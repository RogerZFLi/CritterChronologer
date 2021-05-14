package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(Pet pet) {
        return customerRepository.findOwnerByPets(pet);

    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);

    }

    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(UserNotFoundException::new);
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(UserNotFoundException::new);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(UserNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = employeeRepository.findEmployeesByDaysAvailableAndSkillsIn(employeeRequestDTO.getDate().getDayOfWeek(), employeeRequestDTO.getSkills());
        List<Employee> availableEmployees = new ArrayList<>();
        employees.forEach(employee -> {
            if(employee.getSkills().containsAll(employeeRequestDTO.getSkills())) {
                availableEmployees.add(employee);
            }
        });
        return availableEmployees;
    }

}
