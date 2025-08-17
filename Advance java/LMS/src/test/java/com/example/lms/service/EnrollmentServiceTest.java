package com.example.lms.service;

import com.example.lms.entity.Enrollment;
import com.example.lms.entity.Student;
import com.example.lms.entity.Course;
import com.example.lms.repository.EnrollmentRepository;
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

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Student student = new Student();
        student.setId(1L);

        Course course = new Course();
        course.setId(101L);

        enrollment = new Enrollment();
        enrollment.setId(10L);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
    }

    @Test
    void testGetAllEnrollments() {
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment));

        List<Enrollment> result = enrollmentService.getAllEnrollments();

        assertEquals(1, result.size());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void testGetEnrollmentById() {
        when(enrollmentRepository.findById(10L)).thenReturn(Optional.of(enrollment));

        Optional<Enrollment> result = enrollmentService.getEnrollmentById(10L);

        assertTrue(result.isPresent());
        assertEquals(10L, result.get().getId());
        verify(enrollmentRepository, times(1)).findById(10L);
    }

    @Test
    void testGetEnrollmentsByStudent() {
        when(enrollmentRepository.findByStudent_Id(1L)).thenReturn(Arrays.asList(enrollment));

        List<Enrollment> result = enrollmentService.getEnrollmentsByStudent(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getStudent().getId());
        verify(enrollmentRepository, times(1)).findByStudent_Id(1L);
    }

    @Test
    void testGetEnrollmentsByCourse() {
        when(enrollmentRepository.findByCourse_Id(101L)).thenReturn(Arrays.asList(enrollment));

        List<Enrollment> result = enrollmentService.getEnrollmentsByCourse(101L);

        assertEquals(1, result.size());
        assertEquals(101L, result.get(0).getCourse().getId());
        verify(enrollmentRepository, times(1)).findByCourse_Id(101L);
    }

    @Test
    void testSaveEnrollment() {
        when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);

        Enrollment result = enrollmentService.saveEnrollment(enrollment);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    void testDeleteEnrollment() {
        doNothing().when(enrollmentRepository).deleteById(10L);

        enrollmentService.deleteEnrollment(10L);

        verify(enrollmentRepository, times(1)).deleteById(10L);
    }
}
