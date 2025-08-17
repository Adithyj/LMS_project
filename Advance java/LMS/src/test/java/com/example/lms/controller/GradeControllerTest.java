package com.example.lms.controller;

import com.example.lms.entity.Grade;
import com.example.lms.entity.QuizSubmission;
import com.example.lms.service.GradeService;
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

class GradeControllerTest {

    @Mock
    private GradeService gradeService;

    @InjectMocks
    private GradeController gradeController;

    private Grade grade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        grade = new Grade();
        grade.setId(1L);
        QuizSubmission submission = new QuizSubmission();
        submission.setId(10L);
        grade.setSubmission(submission);
        grade.setScore(95.0);
    }

    @Test
    void testGetAllGrades() {
        when(gradeService.getAllGrades()).thenReturn(Arrays.asList(grade));

        List<Grade> result = gradeController.getAllGrades();

        assertEquals(1, result.size());
        verify(gradeService, times(1)).getAllGrades();
    }

    @Test
    void testGetGradeById_Found() {
        when(gradeService.getGradeById(1L)).thenReturn(Optional.of(grade));

        ResponseEntity<Grade> response = gradeController.getGradeById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(grade, response.getBody());
    }

    @Test
    void testGetGradeById_NotFound() {
        when(gradeService.getGradeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Grade> response = gradeController.getGradeById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetGradeBySubmission() {
        when(gradeService.getGradeBySubmission(10L)).thenReturn(grade);

        Grade result = gradeController.getGradeBySubmission(10L);

        assertEquals(grade, result);
        verify(gradeService, times(1)).getGradeBySubmission(10L);
    }

    @Test
    void testCreateGrade() {
        when(gradeService.saveGrade(grade)).thenReturn(grade);

        Grade result = gradeController.createGrade(grade);

        assertNotNull(result);
        assertEquals(95.0, result.getScore());
        verify(gradeService, times(1)).saveGrade(grade);
    }

    @Test
    void testUpdateGrade_Found() {
        when(gradeService.getGradeById(1L)).thenReturn(Optional.of(grade));
        when(gradeService.saveGrade(grade)).thenReturn(grade);

        ResponseEntity<Grade> response = gradeController.updateGrade(1L, grade);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(grade, response.getBody());
    }

    @Test
    void testUpdateGrade_NotFound() {
        when(gradeService.getGradeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Grade> response = gradeController.updateGrade(1L, grade);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteGrade() {
        doNothing().when(gradeService).deleteGrade(1L);

        ResponseEntity<Void> response = gradeController.deleteGrade(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(gradeService, times(1)).deleteGrade(1L);
    }
}
