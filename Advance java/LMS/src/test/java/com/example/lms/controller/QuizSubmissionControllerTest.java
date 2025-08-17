package com.example.lms.controller;

import com.example.lms.entity.Quiz;
import com.example.lms.entity.QuizSubmission;
import com.example.lms.entity.Student;
import com.example.lms.service.QuizSubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizSubmissionController.class)
class QuizSubmissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizSubmissionService quizSubmissionService;

    @Autowired
    private ObjectMapper objectMapper;

    private QuizSubmission submission;

    @BeforeEach
    void setUp() {
        Student student = new Student();
        student.setId(1L);

        Quiz quiz = new Quiz();
        quiz.setQuizId(1L);

        submission = new QuizSubmission();
        submission.setId(100L);
        submission.setStudent(student);
        submission.setQuiz(quiz);
        submission.setSubmittedAt(LocalDateTime.now());
    }

    @Test
    void testGetAllSubmissions() throws Exception {
        when(quizSubmissionService.getAllSubmissions()).thenReturn(Arrays.asList(submission));

        mockMvc.perform(get("/lms/submissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(100));
    }

    @Test
    void testGetSubmissionById_Found() throws Exception {
        when(quizSubmissionService.getSubmissionById(100L)).thenReturn(Optional.of(submission));

        mockMvc.perform(get("/lms/submissions/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void testGetSubmissionById_NotFound() throws Exception {
        when(quizSubmissionService.getSubmissionById(200L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/lms/submissions/200"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateSubmission() throws Exception {
        when(quizSubmissionService.saveSubmission(any(QuizSubmission.class))).thenReturn(submission);

        mockMvc.perform(post("/lms/submissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(submission)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void testUpdateSubmission() throws Exception {
        when(quizSubmissionService.getSubmissionById(100L)).thenReturn(Optional.of(submission));
        when(quizSubmissionService.saveSubmission(any(QuizSubmission.class))).thenReturn(submission);

        mockMvc.perform(put("/lms/submissions/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(submission)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void testDeleteSubmission() throws Exception {
        Mockito.doNothing().when(quizSubmissionService).deleteSubmission(100L);

        mockMvc.perform(delete("/lms/submissions/100"))
                .andExpect(status().isNoContent());
    }
}
