package com.utm.webhw.dto;

import com.utm.webhw.model.Employee;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    @NotNull
    @NotBlank
    @NotEmpty
    @JsonAlias("first-name")
    private String firstName;

    @NotNull
    @NotBlank
    @NotEmpty
    @JsonAlias("last-name")
    private String lastName;

    private Long department;

    @NotNull
    @NotBlank
    @NotEmpty
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @JsonAlias("phone-number")
    private String phoneNumber;

    @Min(value = 1, message = "Salary should be greater than zero")
    private Double salary;

    public Employee toEmployee() {
        return Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .department(department)
                .email(email)
                .phoneNumber(phoneNumber)
                .salary(salary)
                .build();
    }

    public EmployeeDto(Employee e) {
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.department = e.getDepartment();
        this.email = e.getEmail();
        this.phoneNumber = e.getPhoneNumber();
        this.salary = e.getSalary();
    }
}
