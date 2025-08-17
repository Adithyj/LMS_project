package com.example.lms.controller;

import com.example.lms.entity.Student;
import com.example.lms.service.StudentService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setEmail("john@example.com");

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Smith");
        student2.setEmail("jane@example.com");
    }

    @Test
    void testGetAllStudents() throws Exception {
        Mockito.when(studentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/lms/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].email", is("jane@example.com")));
    }

    @Test
    void testGetStudentById_Found() throws Exception {
        Mockito.when(studentService.getStudentById(1L)).thenReturn(Optional.of(student1));

        mockMvc.perform(get("/lms/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void testGetStudentById_NotFound() throws Exception {
        Mockito.when(studentService.getStudentById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/lms/students/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateStudent() throws Exception {
        Mockito.when(studentService.saveStudent(any(Student.class))).thenReturn(student1);

        mockMvc.perform(post("/lms/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\", \"email\":\"john@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    void testUpdateStudent_Found() throws Exception {
        Mockito.when(studentService.getStudentById(1L)).thenReturn(Optional.of(student1));
        Mockito.when(studentService.saveStudent(any(Student.class))).thenReturn(student1);

        mockMvc.perform(put("/lms/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\", \"email\":\"john@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    void testUpdateStudent_NotFound() throws Exception {
        Mockito.when(studentService.getStudentById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/lms/students/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\", \"email\":\"unknown@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/lms/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetStudentByEmail_Found() throws Exception {
        Mockito.when(studentService.getStudentByEmail(eq("john@example.com"))).thenReturn(Optional.of(student1));

        mockMvc.perform(get("/lms/students/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void testGetStudentByEmail_NotFound() throws Exception {
        Mockito.when(studentService.getStudentByEmail(eq("notfound@example.com"))).thenReturn(Optional.empty());

        mockMvc.perform(get("/lms/students/email/notfound@example.com"))
                .andExpect(status().isNotFound());
    }
}
