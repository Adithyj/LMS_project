package com.example.lms.controller;

import com.example.lms.entity.Quiz;
import com.example.lms.service.QuizService;
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

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quiz = new Quiz();
        quiz.setQuizId(1L);
        quiz.setTitle("Java Basics");
    }

    @Test
    void testGetAllQuizzes() {
        when(quizService.getAllQuizzes()).thenReturn(Arrays.asList(quiz));

        List<Quiz> quizzes = quizController.getAllQuizzes();

        assertNotNull(quizzes);
        assertEquals(1, quizzes.size());
        assertEquals("Java Basics", quizzes.get(0).getTitle());
        verify(quizService, times(1)).getAllQuizzes();
    }

    @Test
    void testGetQuizById_Found() {
        when(quizService.getQuizById(1L)).thenReturn(Optional.of(quiz));

        ResponseEntity<Quiz> response = quizController.getQuizById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(quiz, response.getBody());
    }

    @Test
    void testGetQuizById_NotFound() {
        when(quizService.getQuizById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Quiz> response = quizController.getQuizById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetQuizzesByInstructor() {
        when(quizService.getQuizzesByInstructor(10L)).thenReturn(Arrays.asList(quiz));

        List<Quiz> result = quizController.getQuizzesByInstructor(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(quizService, times(1)).getQuizzesByInstructor(10L);
    }

    @Test
    void testCreateQuiz() {
        when(quizService.saveQuiz(any(Quiz.class))).thenReturn(quiz);

        Quiz result = quizController.createQuiz(quiz);

        assertNotNull(result);
        assertEquals("Java Basics", result.getTitle());
        verify(quizService, times(1)).saveQuiz(quiz);
    }

    @Test
    void testUpdateQuiz_Found() {
        when(quizService.getQuizById(1L)).thenReturn(Optional.of(quiz));
        when(quizService.saveQuiz(any(Quiz.class))).thenReturn(quiz);

        ResponseEntity<Quiz> response = quizController.updateQuiz(1L, quiz);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(quiz, response.getBody());
    }

    @Test
    void testUpdateQuiz_NotFound() {
        when(quizService.getQuizById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Quiz> response = quizController.updateQuiz(1L, quiz);

        assertEquals(404, response.getStatusCodeValue());
        verify(quizService, never()).saveQuiz(any());
    }

    @Test
    void testDeleteQuiz() {
        doNothing().when(quizService).deleteQuiz(1L);

        ResponseEntity<Void> response = quizController.deleteQuiz(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(quizService, times(1)).deleteQuiz(1L);
    }
}
