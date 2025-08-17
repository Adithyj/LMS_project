package com.example.lms.controller;

import com.example.lms.entity.QuizQuestion;
import com.example.lms.service.QuizQuestionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizQuestionController.class)
class QuizQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizQuestionService quizQuestionService;

    @Test
    void testGetAllQuestions() throws Exception {
        QuizQuestion q1 = new QuizQuestion();
        q1.setId(1L);
        q1.setQuestionText("Q1?");
        QuizQuestion q2 = new QuizQuestion();
        q2.setId(2L);
        q2.setQuestionText("Q2?");

        Mockito.when(quizQuestionService.getAllQuestions()).thenReturn(Arrays.asList(q1, q2));

        mockMvc.perform(get("/lms/quiz-questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetQuestionById_Found() throws Exception {
        QuizQuestion q1 = new QuizQuestion();
        q1.setId(1L);
        q1.setQuestionText("Sample?");

        Mockito.when(quizQuestionService.getQuestionById(1L)).thenReturn(Optional.of(q1));

        mockMvc.perform(get("/lms/quiz-questions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.questionText").value("Sample?")); // ✅ fixed
    }

    @Test
    void testGetQuestionById_NotFound() throws Exception {
        Mockito.when(quizQuestionService.getQuestionById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/lms/quiz-questions/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateQuestion() throws Exception {
        QuizQuestion q = new QuizQuestion();
        q.setId(1L);
        q.setQuestionText("New Q");

        Mockito.when(quizQuestionService.saveQuestion(any(QuizQuestion.class))).thenReturn(q);

        mockMvc.perform(post("/lms/quiz-questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionText\":\"New Q\"}")) // ✅ fixed
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.questionText").value("New Q")); // ✅ fixed
    }

    @Test
    void testUpdateQuestion_Found() throws Exception {
        QuizQuestion q = new QuizQuestion();
        q.setId(1L);
        q.setQuestionText("Updated Q");

        Mockito.when(quizQuestionService.getQuestionById(1L)).thenReturn(Optional.of(q));
        Mockito.when(quizQuestionService.saveQuestion(any(QuizQuestion.class))).thenReturn(q);

        mockMvc.perform(put("/lms/quiz-questions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionText\":\"Updated Q\"}")) // ✅ fixed
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.questionText").value("Updated Q")); // ✅ fixed
    }

    @Test
    void testUpdateQuestion_NotFound() throws Exception {
        Mockito.when(quizQuestionService.getQuestionById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/lms/quiz-questions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionText\":\"Updated Q\"}")) // ✅ fixed
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteQuestion() throws Exception {
        mockMvc.perform(delete("/lms/quiz-questions/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(quizQuestionService).deleteQuestion(1L);
    }
}
