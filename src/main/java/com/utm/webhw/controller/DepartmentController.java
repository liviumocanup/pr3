package com.utm.webhw.controller;

import com.utm.webhw.dto.DepartmentDto;
import com.utm.webhw.model.Department;
import com.utm.webhw.service.DepartmentService;
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
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDto> findAll() {
        return departmentService.findAll();
    }

    @GetMapping("{id}")
    public DepartmentDto findById(@PathVariable long id) {
        return departmentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DepartmentDto departmentDto) {
        Department createdDepartment = departmentService.create(departmentDto.toDepartment());

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .build(createdDepartment.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(new DepartmentDto(createdDepartment));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody DepartmentDto departmentDto) {
        Department updatedDepartment = departmentService.update(id, departmentDto.toDepartment());

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .build(updatedDepartment.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .location(uri)
                .body(new DepartmentDto(updatedDepartment));
    }
}
