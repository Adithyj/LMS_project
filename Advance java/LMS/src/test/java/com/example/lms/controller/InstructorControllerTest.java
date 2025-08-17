package com.example.lms.controller;

import com.example.lms.entity.Instructor;
import com.example.lms.service.InstructorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InstructorController.class)
class InstructorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstructorService instructorService;

    private Instructor instructor;

    @BeforeEach
    void setup() {
        instructor = new Instructor();
        instructor.setInstructorId(1L);
        instructor.setName("John Doe");
        instructor.setEmail("john.doe@example.com");
    }

    @Test
    void testGetAllInstructors() throws Exception {
        Mockito.when(instructorService.getAllInstructors())
                .thenReturn(Arrays.asList(instructor));

        mockMvc.perform(get("/lms/instructors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    void testGetInstructorById_Found() throws Exception {
        Mockito.when(instructorService.getInstructorById(1L))
                .thenReturn(Optional.of(instructor));

        mockMvc.perform(get("/lms/instructors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testGetInstructorById_NotFound() throws Exception {
        Mockito.when(instructorService.getInstructorById(2L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/lms/instructors/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateInstructor() throws Exception {
        Mockito.when(instructorService.saveInstructor(any(Instructor.class)))
                .thenReturn(instructor);

        mockMvc.perform(post("/lms/instructors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testUpdateInstructor_Found() throws Exception {
        Mockito.when(instructorService.getInstructorById(1L))
                .thenReturn(Optional.of(instructor));
        Mockito.when(instructorService.saveInstructor(any(Instructor.class)))
                .thenReturn(instructor);

        mockMvc.perform(put("/lms/instructors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Updated\", \"email\": \"john.updated@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe")) // Because mock returns original instructor
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testUpdateInstructor_NotFound() throws Exception {
        Mockito.when(instructorService.getInstructorById(2L))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/lms/instructors/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Not Found\", \"email\": \"nf@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteInstructor() throws Exception {
        Mockito.doNothing().when(instructorService).deleteInstructor(1L);

        mockMvc.perform(delete("/lms/instructors/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetInstructorByEmail_Found() throws Exception {
        Mockito.when(instructorService.getInstructorByEmail("john.doe@example.com"))
                .thenReturn(Optional.of(instructor));

        mockMvc.perform(get("/lms/instructors/search")
                        .param("email", "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetInstructorByEmail_NotFound() throws Exception {
        Mockito.when(instructorService.getInstructorByEmail("notfound@example.com"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/lms/instructors/search")
                        .param("email", "notfound@example.com"))
                .andExpect(status().isNotFound());
    }
}
