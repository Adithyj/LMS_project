package com.example.lms.service;

import com.example.lms.entity.Instructor;
import com.example.lms.repository.InstructorRepository;
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

class InstructorServiceTest {

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private InstructorService instructorService;

    private Instructor instructor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instructor = new Instructor();
        instructor.setInstructorId(1L);
        instructor.setName("John Doe");
        instructor.setEmail("john@example.com");
    }

    @Test
    void testGetAllInstructors() {
        when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor));

        List<Instructor> result = instructorService.getAllInstructors();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void testGetInstructorById_Found() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        Optional<Instructor> result = instructorService.getInstructorById(1L);

        assertTrue(result.isPresent());
        assertEquals("john@example.com", result.get().getEmail());
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetInstructorById_NotFound() {
        when(instructorRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Instructor> result = instructorService.getInstructorById(99L);

        assertFalse(result.isPresent());
        verify(instructorRepository, times(1)).findById(99L);
    }

    @Test
    void testGetInstructorByEmail_Found() {
        when(instructorRepository.findByEmail("john@example.com")).thenReturn(instructor);

        Optional<Instructor> result = instructorService.getInstructorByEmail("john@example.com");

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(instructorRepository, times(1)).findByEmail("john@example.com");
    }

    @Test
    void testGetInstructorByEmail_NotFound() {
        when(instructorRepository.findByEmail("notfound@example.com")).thenReturn(null);

        Optional<Instructor> result = instructorService.getInstructorByEmail("notfound@example.com");

        assertFalse(result.isPresent());
        verify(instructorRepository, times(1)).findByEmail("notfound@example.com");
    }

    @Test
    void testSaveInstructor() {
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor saved = instructorService.saveInstructor(instructor);

        assertNotNull(saved);
        assertEquals("john@example.com", saved.getEmail());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testDeleteInstructor() {
        doNothing().when(instructorRepository).deleteById(1L);

        instructorService.deleteInstructor(1L);

        verify(instructorRepository, times(1)).deleteById(1L);
    }
}
