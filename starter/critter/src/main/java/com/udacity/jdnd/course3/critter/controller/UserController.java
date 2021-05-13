package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PetService petService;


    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToCustomer(customerDTO);

        List<Pet> pets = customerDTO.getPetIds()==null? null : customerDTO.getPetIds().stream().map(id->{
            Pet pet = petService.getPetById(id);
            return pet;
        }).collect(Collectors.toList());
        customer.setPets(pets);
        customer = userService.saveCustomer(customer);
        customerDTO.setId(customer.getId());
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = userService.getAllCustomers();
        return customers.stream().map(customer->{
            CustomerDTO customerDTO = convertCustomerToCustomerDTO(customer);
            return customerDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPetById(petId);
        CustomerDTO customerDTO = convertCustomerToCustomerDTO(userService.getOwnerByPet(pet));
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return convertEmployeeToEmployeeDTO(userService.saveEmployee(convertEmployeeDTOToEmployee(employeeDTO)));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeDTO employeeDTO = convertEmployeeToEmployeeDTO(userService.getEmployeeById(employeeId));
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = userService.findEmployeesForService(employeeRequestDTO);
        return employees.stream().map(employee -> {
            EmployeeDTO employeeDTO = convertEmployeeToEmployeeDTO(employee);
            return employeeDTO;
        }).collect(Collectors.toList());

    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = null;
        if (employee != null){
            employeeDTO = new EmployeeDTO();
            BeanUtils.copyProperties(employee, employeeDTO);
        }
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private CustomerDTO convertCustomerToCustomerDTO (Customer customer) {
        CustomerDTO customerDTO = null;
        if ( customer != null ) {
            customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customer, customerDTO);
            customerDTO.setPetIds(customer.getPets() == null ? null : customer.getPets().stream().map(pet -> {
                Long id = pet.getId();
                return id;
            }).collect(Collectors.toList()));
        }
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer (CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
