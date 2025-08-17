package com.example.lms.service;

import com.example.lms.entity.Student;
import com.example.lms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setEmail("test@example.com");
        student.setName("Test Student");
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));

        List<Student> students = studentService.getAllStudents();

        assertNotNull(students);
        assertEquals(1, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentById(1L);

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.getStudentById(2L);

        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(2L);
    }

    @Test
    void testSaveStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student saved = studentService.saveStudent(student);

        assertNotNull(saved);
        assertEquals("test@example.com", saved.getEmail());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetStudentByEmail_Found() {
        when(studentRepository.findByEmail("test@example.com")).thenReturn(student);

        Optional<Student> result = studentService.getStudentByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(studentRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testGetStudentByEmail_NotFound() {
        when(studentRepository.findByEmail("notfound@example.com")).thenReturn(null);

        Optional<Student> result = studentService.getStudentByEmail("notfound@example.com");

        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findByEmail("notfound@example.com");
    }
}
