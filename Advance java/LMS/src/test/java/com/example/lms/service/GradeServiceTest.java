package com.example.lms.service;

import com.example.lms.entity.Grade;
import com.example.lms.entity.QuizSubmission;

import com.example.lms.repository.GradeRepository;
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

class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    private Grade grade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        QuizSubmission submission = new QuizSubmission();
        submission.setId(1L);

        grade = new Grade();
        grade.setId(1L);
        grade.setScore(85.0);
        grade.setSubmission(submission);
    }

    @Test
    void testGetAllGrades() {
        when(gradeRepository.findAll()).thenReturn(Arrays.asList(grade));

        List<Grade> result = gradeService.getAllGrades();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(85.0, result.get(0).getScore());
        verify(gradeRepository, times(1)).findAll();
    }

    @Test
    void testGetGradeById() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));

        Optional<Grade> result = gradeService.getGradeById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(gradeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetGradeBySubmission_Found() {
        when(gradeRepository.findBySubmission_Id(1L)).thenReturn(grade);

        Grade result = gradeService.getGradeBySubmission(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(85.0, result.getScore());
        verify(gradeRepository, times(1)).findBySubmission_Id(1L);
    }

    @Test
    void testGetGradeBySubmission_NotFound() {
        when(gradeRepository.findBySubmission_Id(99L)).thenReturn(null);

        Grade result = gradeService.getGradeBySubmission(99L);

        assertNull(result);
        verify(gradeRepository, times(1)).findBySubmission_Id(99L);
    }

    @Test
    void testSaveGrade() {
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

        Grade result = gradeService.saveGrade(grade);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(85.0, result.getScore());
        verify(gradeRepository, times(1)).save(grade);
    }

    @Test
    void testDeleteGrade() {
        doNothing().when(gradeRepository).deleteById(1L);

        gradeService.deleteGrade(1L);

        verify(gradeRepository, times(1)).deleteById(1L);
    }
}
