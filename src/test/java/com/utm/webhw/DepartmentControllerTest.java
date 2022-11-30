package com.utm.webhw;

import com.utm.webhw.controller.DepartmentController;
import com.utm.webhw.controller.EmployeeController;
import com.utm.webhw.dto.DepartmentDto;
import com.utm.webhw.exception.EntityNotFoundException;
import com.utm.webhw.model.Department;
import com.utm.webhw.service.DepartmentService;
import com.utm.webhw.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private DepartmentController departmentController;

    private Department expectedDepartment;
    private String jsonDepartment;
    private ResponseEntity<?> responseEntity;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        expectedDepartment = new DepartmentDto("IT", "Chisinau").toDepartment();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonDepartment = objectMapper.writeValueAsString(new DepartmentDto("IT", "Chisinau"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String myUrl = "http://localhost:8080/departments/1";
        URI uri = new URI(myUrl);

        responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .build();
    }

    @Test
    void testFindAllShouldReturnEmptyListWhenRepositoryEmpty() throws Exception {
        doReturn(new ArrayList<Department>()).when(departmentService).findAll();

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("body").doesNotExist());
    }

    @Test
    void testFindByIdShouldThrowExceptionWhenThereIsNoSuchId() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Department with id 1 not found")).when(departmentService).findById(1L);

        mockMvc.perform(get("/departments/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Department with id 1 not found"))
                .andExpect(jsonPath("error").value("Entity not found"));
    }

    @Test
    void testFindByIdThrowExceptionForNonNumericIdProvided() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(departmentService).findById(any());

        mockMvc.perform(get("/departments/a"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Parameter: id, has invalid value: a"))
                .andExpect(jsonPath("error").value("Bad parameter value"));
    }

    @Test
    void testFindByIdObjectFieldsShouldMatchWhenIdFound() throws Exception {
        doReturn(new DepartmentDto(expectedDepartment)).when(departmentService).findById(1L);

        mockMvc.perform(get("/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedDepartment.getName()))
                .andExpect(jsonPath("$.location").value(expectedDepartment.getLocation()));
    }

    @Test
    void testCreateExpectedUriShouldBeCorrectAndObjectFieldsMatchIfTheProvidedBodyIsValid() throws Exception {
        doReturn(expectedDepartment).when(departmentService).create(any(Department.class));

        mockMvc.perform(
                        post("/departments")
                                .content(jsonDepartment)
                                .characterEncoding("utf-8")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(expectedDepartment.getName()))
                .andExpect(jsonPath("$.location").value(expectedDepartment.getLocation()))
                .andReturn().getResponse().getHeaders(responseEntity.getHeaders().toString());
    }

    @Test
    void testUpdateShouldThrowExceptionIfIdNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Department with id 1 not found")).when(departmentService).update(eq(1L), any(Department.class));

        mockMvc.perform(
                        put("/departments/1")
                                .content(jsonDepartment)
                                .characterEncoding("utf-8")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Department with id 1 not found"))
                .andExpect(jsonPath("error").value("Entity not found"));
    }

    @Test
    void testUpdateExpectedUriShouldBeCorrectAndObjectFieldsMatchIfTheProvidedBodyIsValid() throws Exception {
        expectedDepartment.setId(1L);

        doReturn(expectedDepartment).when(departmentService).update(eq(1L), any(Department.class));

        mockMvc.perform(
                        put("/departments/1")
                                .content(jsonDepartment)
                                .characterEncoding("utf-8")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedDepartment.getName()))
                .andExpect(jsonPath("$.location").value(expectedDepartment.getLocation()))
                .andReturn().getResponse().getHeaders(responseEntity.getHeaders().toString());
    }
}
