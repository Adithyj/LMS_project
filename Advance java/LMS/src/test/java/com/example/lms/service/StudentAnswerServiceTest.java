package com.example.lms.service;

import com.example.lms.entity.StudentAnswer;
import com.example.lms.entity.QuizSubmission;
import com.example.lms.entity.QuizQuestion;
import com.example.lms.repository.StudentAnswerRepository;
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

class StudentAnswerServiceTest {

    @Mock
    private StudentAnswerRepository studentAnswerRepository;

    @InjectMocks
    private StudentAnswerService studentAnswerService;

    private StudentAnswer studentAnswer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        QuizSubmission submission = new QuizSubmission();
        submission.setId(1L);

        QuizQuestion question = new QuizQuestion();
        question.setId(1L);

        studentAnswer = new StudentAnswer();
        studentAnswer.setId(1L);
        studentAnswer.setSubmission(submission);
        studentAnswer.setQuestion(question);
        studentAnswer.setSelectedAnswer("A");
        studentAnswer.setIsCorrect(true);
    }

    @Test
    void testGetAllAnswers() {
        when(studentAnswerRepository.findAll()).thenReturn(Arrays.asList(studentAnswer));

        List<StudentAnswer> answers = studentAnswerService.getAllAnswers();

        assertEquals(1, answers.size());
        assertEquals("A", answers.get(0).getSelectedAnswer());
        assertTrue(answers.get(0).getIsCorrect());
        verify(studentAnswerRepository, times(1)).findAll();
    }

    @Test
    void testGetAnswerById_Found() {
        when(studentAnswerRepository.findById(1L)).thenReturn(Optional.of(studentAnswer));

        Optional<StudentAnswer> result = studentAnswerService.getAnswerById(1L);

        assertTrue(result.isPresent());
        assertEquals("A", result.get().getSelectedAnswer());
        assertTrue(result.get().getIsCorrect());
        verify(studentAnswerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAnswerById_NotFound() {
        when(studentAnswerRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<StudentAnswer> result = studentAnswerService.getAnswerById(2L);

        assertFalse(result.isPresent());
        verify(studentAnswerRepository, times(1)).findById(2L);
    }

    @Test
    void testGetAnswersBySubmission() {
        when(studentAnswerRepository.findBySubmission_Id(1L)).thenReturn(Arrays.asList(studentAnswer));

        List<StudentAnswer> answers = studentAnswerService.getAnswersBySubmission(1L);

        assertEquals(1, answers.size());
        assertEquals("A", answers.get(0).getSelectedAnswer());
        verify(studentAnswerRepository, times(1)).findBySubmission_Id(1L);
    }

    @Test
    void testGetAnswersByQuestion() {
        when(studentAnswerRepository.findByQuestion_Id(1L)).thenReturn(Arrays.asList(studentAnswer));

        List<StudentAnswer> answers = studentAnswerService.getAnswersByQuestion(1L);

        assertEquals(1, answers.size());
        assertEquals("A", answers.get(0).getSelectedAnswer());
        verify(studentAnswerRepository, times(1)).findByQuestion_Id(1L);
    }

    @Test
    void testSaveAnswer() {
        when(studentAnswerRepository.save(any(StudentAnswer.class))).thenReturn(studentAnswer);

        StudentAnswer saved = studentAnswerService.saveAnswer(studentAnswer);

        assertNotNull(saved);
        assertEquals("A", saved.getSelectedAnswer());
        assertTrue(saved.getIsCorrect());
        verify(studentAnswerRepository, times(1)).save(studentAnswer);
    }

    @Test
    void testDeleteAnswer() {
        doNothing().when(studentAnswerRepository).deleteById(1L);

        studentAnswerService.deleteAnswer(1L);

        verify(studentAnswerRepository, times(1)).deleteById(1L);
    }
}
