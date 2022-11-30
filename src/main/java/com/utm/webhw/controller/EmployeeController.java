package com.utm.webhw.controller;

import com.utm.webhw.dto.EmployeeDto;
import com.utm.webhw.model.Employee;
import com.utm.webhw.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("{id}")
    public EmployeeDto findById(@PathVariable long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee createdEmployee = employeeService.create(employeeDto.toEmployee());

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .build(createdEmployee.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(new EmployeeDto(createdEmployee));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody EmployeeDto employeeDto) {
        Employee updatedEmployee = employeeService.update(id, employeeDto.toEmployee());

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .build(updatedEmployee.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .location(uri)
                .body(new EmployeeDto(updatedEmployee));
    }
}
