package com.utm.webhw;

import com.utm.webhw.dto.DepartmentDto;
import com.utm.webhw.exception.EntityNotFoundException;
import com.utm.webhw.model.Department;
import com.utm.webhw.repository.DepartmentRepository;
import com.utm.webhw.service.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;

public class DepartmentServiceTest {
    private final DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
    private DepartmentService departmentService;

    private Department expectedDepartment;

    @BeforeEach
    void setUp() {
        departmentService = new DepartmentService(departmentRepository);
        expectedDepartment = new DepartmentDto("IT", "Chisinau").toDepartment();
    }

    @Test
    void testFindAllShouldReturnEmptyListWhenRepositoryEmpty() {
        doReturn(new ArrayList<>()).when(departmentRepository).findAll();

        assertTrue(departmentService.findAll().isEmpty());
    }

    @Test
    void testFindByIdShouldThrowExceptionWhenThereIsNoSuchId() {
        doReturn(Optional.empty()).when(departmentRepository).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> departmentService.findById(1L));
    }

    @Test
    void testFindByIdObjectFieldsShouldMatchWhenIdFound() {
        doReturn(Optional.of(expectedDepartment)).when(departmentRepository).findById(1L);

        assertEquals(new DepartmentDto(expectedDepartment), departmentService.findById(1L));
    }

    @Test
    void testCreateObjectFieldsMatchIfTheProvidedBodyIsValid() {
        doReturn(expectedDepartment).when(departmentRepository).save(expectedDepartment);

        Assertions.assertEquals(expectedDepartment, departmentService.create(expectedDepartment));
    }

    @Test
    void testUpdateShouldThrowExceptionIfIdNotFound() {
        doReturn(Optional.empty()).when(departmentRepository).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> departmentService.update(1L, expectedDepartment));
    }

    @Test
    void testUpdateObjectFieldsMatchIfTheProvidedBodyIsValid() {
        doReturn(Optional.of(expectedDepartment)).when(departmentRepository).findById(1L);
        doReturn(expectedDepartment).when(departmentRepository).save(expectedDepartment);

        Assertions.assertEquals(expectedDepartment, departmentService.update(1L, expectedDepartment));
    }
}
