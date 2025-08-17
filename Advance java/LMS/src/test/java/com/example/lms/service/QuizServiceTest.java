package com.example.lms.service;

import com.example.lms.entity.Quiz;
import com.example.lms.repository.QuizRepository;
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

class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quiz = new Quiz();
        quiz.setQuizId(1L);
        quiz.setTitle("Sample Quiz");
    }

    @Test
    void testGetAllQuizzes() {
        when(quizRepository.findAll()).thenReturn(Arrays.asList(quiz));

        List<Quiz> quizzes = quizService.getAllQuizzes();

        assertNotNull(quizzes);
        assertEquals(1, quizzes.size());
        verify(quizRepository, times(1)).findAll();
    }

    @Test
    void testGetQuizById_Found() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        Optional<Quiz> result = quizService.getQuizById(1L);

        assertTrue(result.isPresent());
        assertEquals("Sample Quiz", result.get().getTitle());
        verify(quizRepository, times(1)).findById(1L);
    }

    @Test
    void testGetQuizById_NotFound() {
        when(quizRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Quiz> result = quizService.getQuizById(2L);

        assertFalse(result.isPresent());
        verify(quizRepository, times(1)).findById(2L);
    }

    @Test
    void testGetQuizzesByInstructor() {
        when(quizRepository.findByInstructor(100L)).thenReturn(Arrays.asList(quiz));

        List<Quiz> quizzes = quizService.getQuizzesByInstructor(100L);

        assertEquals(1, quizzes.size());
        assertEquals("Sample Quiz", quizzes.get(0).getTitle());
        verify(quizRepository, times(1)).findByInstructor(100L);
    }

    @Test
    void testSaveQuiz() {
        when(quizRepository.save(quiz)).thenReturn(quiz);

        Quiz savedQuiz = quizService.saveQuiz(quiz);

        assertNotNull(savedQuiz);
        assertEquals("Sample Quiz", savedQuiz.getTitle());
        verify(quizRepository, times(1)).save(quiz);
    }

    @Test
    void testDeleteQuiz() {
        doNothing().when(quizRepository).deleteById(1L);

        quizService.deleteQuiz(1L);

        verify(quizRepository, times(1)).deleteById(1L);
    }
}
