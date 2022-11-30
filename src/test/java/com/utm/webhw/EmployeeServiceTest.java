package com.utm.webhw;

import com.utm.webhw.dto.EmployeeDto;
import com.utm.webhw.exception.EntityNotFoundException;
import com.utm.webhw.model.Employee;
import com.utm.webhw.repository.EmployeeRepository;
import com.utm.webhw.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class EmployeeServiceTest {
    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private EmployeeService employeeService;

    private Employee expectedEmployee;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepository);
        expectedEmployee = new EmployeeDto("John", "Doe", 1L, "jdoe@mail.com",
                "+3738404404", 500.0).toEmployee();
    }

    @Test
    void testFindAllShouldReturnEmptyListWhenRepositoryEmpty() {
        doReturn(new ArrayList<>()).when(employeeRepository).findAll();

        assertTrue(employeeService.findAll().isEmpty());
    }

    @Test
    void testFindByIdShouldThrowExceptionWhenThereIsNoSuchId() {
        doReturn(Optional.empty()).when(employeeRepository).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> employeeService.findById(1L));
    }

    @Test
    void testFindByIdObjectFieldsShouldMatchWhenIdFound() {
        doReturn(Optional.of(expectedEmployee)).when(employeeRepository).findById(1L);

        assertEquals(new EmployeeDto(expectedEmployee), employeeService.findById(1L));
    }

    @Test
    void testCreateObjectFieldsMatchIfTheProvidedBodyIsValid() {
        doReturn(expectedEmployee).when(employeeRepository).save(expectedEmployee);

        assertEquals(expectedEmployee, employeeService.create(expectedEmployee));
    }

    @Test
    void testUpdateShouldThrowExceptionIfIdNotFound() {
        doReturn(Optional.empty()).when(employeeRepository).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> employeeService.update(1L, expectedEmployee));
    }

    @Test
    void testUpdateObjectFieldsMatchIfTheProvidedBodyIsValid() {
        doReturn(Optional.of(expectedEmployee)).when(employeeRepository).findById(1L);
        doReturn(expectedEmployee).when(employeeRepository).save(expectedEmployee);

        assertEquals(expectedEmployee, employeeService.update(1L, expectedEmployee));
    }
}
