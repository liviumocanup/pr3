package com.utm.webhw.dto;

import com.utm.webhw.model.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank
    private String location;

    public Department toDepartment() {
        return Department.builder()
                .name(name)
                .location(location)
                .build();
    }

    public DepartmentDto(Department department) {
        this.name = department.getName();
        this.location = department.getLocation();
    }
}
