package com.utm.webhw.service;

import com.utm.webhw.dto.EmployeeDto;
import com.utm.webhw.exception.EntityNotFoundException;
import com.utm.webhw.model.Employee;
import com.utm.webhw.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAll() {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<Employee> allEmployeesList = employeeRepository.findAll();

        for (Employee e : allEmployeesList) {
            employeeDtos.add(new EmployeeDto(e));
        }

        return employeeDtos;
    }

    @Transactional(readOnly = true)
    public EmployeeDto findById(Long id) {
        Employee foundEmployee = findByIdForEmployee(id);
        return new EmployeeDto(foundEmployee);
    }

    private Employee findByIdForEmployee(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee with id: " + id + " not found."));
    }

    @Transactional
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(long id, Employee employee) {
        Employee updatedEmployee = findByIdForEmployee(id);

        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setLastName(employee.getLastName());
        updatedEmployee.setDepartment(employee.getDepartment());
        updatedEmployee.setEmail(employee.getEmail());
        updatedEmployee.setPhoneNumber(employee.getPhoneNumber());
        updatedEmployee.setSalary(employee.getSalary());

        return updatedEmployee;
    }
}
