package com.example.lms.controller;

import com.example.lms.entity.Enrollment;
import com.example.lms.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        enrollment = new Enrollment();
        enrollment.setId(1L);
    }

    @Test
    void testGetAllEnrollments() {
        when(enrollmentService.getAllEnrollments()).thenReturn(Arrays.asList(enrollment));

        List<Enrollment> result = enrollmentController.getAllEnrollments();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(enrollmentService, times(1)).getAllEnrollments();
    }

    @Test
    void testGetEnrollmentById_Found() {
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(Optional.of(enrollment));

        ResponseEntity<Enrollment> response = enrollmentController.getEnrollmentById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(enrollment, response.getBody());
        verify(enrollmentService, times(1)).getEnrollmentById(1L);
    }

    @Test
    void testGetEnrollmentById_NotFound() {
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Enrollment> response = enrollmentController.getEnrollmentById(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
        assertNull(response.getBody());
        verify(enrollmentService, times(1)).getEnrollmentById(1L);
    }

    @Test
    void testGetEnrollmentsByStudent() {
        when(enrollmentService.getEnrollmentsByStudent(10L)).thenReturn(Arrays.asList(enrollment));

        List<Enrollment> result = enrollmentController.getEnrollmentsByStudent(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(enrollmentService, times(1)).getEnrollmentsByStudent(10L);
    }

    @Test
    void testGetEnrollmentsByCourse() {
        when(enrollmentService.getEnrollmentsByCourse(20L)).thenReturn(Arrays.asList(enrollment));

        List<Enrollment> result = enrollmentController.getEnrollmentsByCourse(20L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(enrollmentService, times(1)).getEnrollmentsByCourse(20L);
    }

    @Test
    void testCreateEnrollment() {
        when(enrollmentService.saveEnrollment(enrollment)).thenReturn(enrollment);

        Enrollment result = enrollmentController.createEnrollment(enrollment);

        assertNotNull(result);
        assertEquals(enrollment.getId(), result.getId());
        verify(enrollmentService, times(1)).saveEnrollment(enrollment);
    }

    @Test
    void testUpdateEnrollment_Found() {
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentService.saveEnrollment(any(Enrollment.class))).thenReturn(enrollment);

        ResponseEntity<Enrollment> response = enrollmentController.updateEnrollment(1L, enrollment);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(enrollment, response.getBody());
        verify(enrollmentService, times(1)).getEnrollmentById(1L);
        verify(enrollmentService, times(1)).saveEnrollment(enrollment);
    }

    @Test
    void testUpdateEnrollment_NotFound() {
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Enrollment> response = enrollmentController.updateEnrollment(1L, enrollment);

        assertTrue(response.getStatusCode().is4xxClientError());
        assertNull(response.getBody());
        verify(enrollmentService, times(1)).getEnrollmentById(1L);
    }

    @Test
    void testDeleteEnrollment_Success() {
        doNothing().when(enrollmentService).deleteEnrollment(1L);

        ResponseEntity<Void> response = enrollmentController.deleteEnrollment(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(enrollmentService, times(1)).deleteEnrollment(1L);
    }

    @Test
    void testDeleteEnrollment_Failure() {
        doThrow(new RuntimeException("Error deleting enrollment")).when(enrollmentService).deleteEnrollment(1L);

        ResponseEntity<Void> response = enrollmentController.deleteEnrollment(1L);

        assertTrue(response.getStatusCode().is5xxServerError());
        verify(enrollmentService, times(1)).deleteEnrollment(1L);
    }
}
