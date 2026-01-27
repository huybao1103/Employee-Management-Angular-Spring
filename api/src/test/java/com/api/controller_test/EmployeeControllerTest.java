package com.api.controller_test;

import com.api.controllers.EmployeeController;
import com.api.models.Employee.EmployeeModel;
import com.api.models.Employee.EmployeeUpdateModel;
import com.api.services.interfaces.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IEmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    // Helper to build an EmployeeModel from JSON (avoids depending on constructors)
    private EmployeeUpdateModel sampleEmployee() throws Exception {
        String json = "{" +
                "\"name\":\"John Doe\"," +
                "\"email\":\"john.doe@example.com\"," +
                "\"department\":\"IT\"," +
                "\"salary\":\"99999\"}";
        return objectMapper.readValue(json, EmployeeUpdateModel.class);
    }

    @Test
    public void createEmployee_returnsCreatedEmployee() throws Exception {
        EmployeeUpdateModel employee = sampleEmployee();

        when(employeeService.createEmployee(any(EmployeeUpdateModel.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andExpect(jsonPath("$.salary").value("99999"));

        verify(employeeService, times(1)).createEmployee(any(EmployeeUpdateModel.class));
    }
}
