package com.example.lms.service;

import com.example.lms.entity.QuizQuestion;
import com.example.lms.repository.QuizQuestionRepository;
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

class QuizQuestionServiceTest {

    @Mock
    private QuizQuestionRepository quizQuestionRepository;

    @InjectMocks
    private QuizQuestionService quizQuestionService;

    private QuizQuestion question;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        question = new QuizQuestion();
        question.setId(1L);
        question.setQuestionText("What is Java?");
    }

    @Test
    void testGetAllQuestions() {
        when(quizQuestionRepository.findAll()).thenReturn(Arrays.asList(question));

        List<QuizQuestion> result = quizQuestionService.getAllQuestions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("What is Java?", result.get(0).getQuestionText());
        verify(quizQuestionRepository, times(1)).findAll();
    }

    @Test
    void testGetQuestionById_Found() {
        when(quizQuestionRepository.findById(1L)).thenReturn(Optional.of(question));

        Optional<QuizQuestion> result = quizQuestionService.getQuestionById(1L);

        assertTrue(result.isPresent());
        assertEquals("What is Java?", result.get().getQuestionText());
        verify(quizQuestionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetQuestionById_NotFound() {
        when(quizQuestionRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<QuizQuestion> result = quizQuestionService.getQuestionById(2L);

        assertFalse(result.isPresent());
        verify(quizQuestionRepository, times(1)).findById(2L);
    }

    @Test
    void testGetQuestionsByQuiz() {
        when(quizQuestionRepository.findByQuiz_Id(10L)).thenReturn(Arrays.asList(question));

        List<QuizQuestion> result = quizQuestionService.getQuestionsByQuiz(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(quizQuestionRepository, times(1)).findByQuiz_Id(10L);
    }

    @Test
    void testSaveQuestion() {
        when(quizQuestionRepository.save(question)).thenReturn(question);

        QuizQuestion result = quizQuestionService.saveQuestion(question);

        assertNotNull(result);
        assertEquals("What is Java?", result.getQuestionText());
        verify(quizQuestionRepository, times(1)).save(question);
    }

    @Test
    void testDeleteQuestion() {
        doNothing().when(quizQuestionRepository).deleteById(1L);

        quizQuestionService.deleteQuestion(1L);

        verify(quizQuestionRepository, times(1)).deleteById(1L);
    }
}
