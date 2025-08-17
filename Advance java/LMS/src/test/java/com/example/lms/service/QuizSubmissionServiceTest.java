package com.example.lms.service;

import com.example.lms.entity.Quiz;
import com.example.lms.entity.QuizSubmission;
import com.example.lms.entity.Student;
import com.example.lms.repository.QuizSubmissionRepository;
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

class QuizSubmissionServiceTest {

    @Mock
    private QuizSubmissionRepository quizSubmissionRepository;

    @InjectMocks
    private QuizSubmissionService quizSubmissionService;

    private QuizSubmission submission;
    private Student student;
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);

        quiz = new Quiz();
        quiz.setQuizId(10L);

        submission = new QuizSubmission();
        submission.setId(100L);
        submission.setStudent(student);
        submission.setQuiz(quiz);
    }

    @Test
    void testGetAllSubmissions() {
        when(quizSubmissionRepository.findAll()).thenReturn(Arrays.asList(submission));

        List<QuizSubmission> result = quizSubmissionService.getAllSubmissions();

        assertEquals(1, result.size());
        verify(quizSubmissionRepository, times(1)).findAll();
    }

    @Test
    void testGetSubmissionByIdFound() {
        when(quizSubmissionRepository.findById(100L)).thenReturn(Optional.of(submission));

        Optional<QuizSubmission> result = quizSubmissionService.getSubmissionById(100L);

        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getId());
        verify(quizSubmissionRepository, times(1)).findById(100L);
    }

    @Test
    void testGetSubmissionByIdNotFound() {
        when(quizSubmissionRepository.findById(200L)).thenReturn(Optional.empty());

        Optional<QuizSubmission> result = quizSubmissionService.getSubmissionById(200L);

        assertFalse(result.isPresent());
        verify(quizSubmissionRepository, times(1)).findById(200L);
    }

    @Test
    void testGetSubmissionsByStudent() {
        when(quizSubmissionRepository.findByStudent_Id(1L)).thenReturn(Arrays.asList(submission));

        List<QuizSubmission> result = quizSubmissionService.getSubmissionsByStudent(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getStudent().getId());
        verify(quizSubmissionRepository, times(1)).findByStudent_Id(1L);
    }

    @Test
    void testGetSubmissionsByQuiz() {
        when(quizSubmissionRepository.findByQuiz_Id(10L)).thenReturn(Arrays.asList(submission));

        List<QuizSubmission> result = quizSubmissionService.getSubmissionsByQuiz(10L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getQuiz().getQuizId());
        verify(quizSubmissionRepository, times(1)).findByQuiz_Id(10L);
    }

    @Test
    void testSaveSubmission() {
        when(quizSubmissionRepository.save(submission)).thenReturn(submission);

        QuizSubmission saved = quizSubmissionService.saveSubmission(submission);

        assertNotNull(saved);
        assertEquals(100L, saved.getId());
        verify(quizSubmissionRepository, times(1)).save(submission);
    }

    @Test
    void testDeleteSubmission() {
        doNothing().when(quizSubmissionRepository).deleteById(100L);

        quizSubmissionService.deleteSubmission(100L);

        verify(quizSubmissionRepository, times(1)).deleteById(100L);
    }
}
