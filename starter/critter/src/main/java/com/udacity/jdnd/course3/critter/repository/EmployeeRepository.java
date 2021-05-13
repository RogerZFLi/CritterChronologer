package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeById(Long employeeId);

    @Modifying
    @Query("update Employee e set e.daysAvailable= :daysAvailable where e.id= :employeeId")
    void updateEmployeeSetAvailability(Set<DayOfWeek> daysAvailable, Long employeeId);

    List<Employee> findByIdIn(List<Long> employeeIds);

    List<Employee> findEmployeesByDaysAvailableAndSkillsIn(DayOfWeek daysAvailable,Set<EmployeeSkill> skills);
}
