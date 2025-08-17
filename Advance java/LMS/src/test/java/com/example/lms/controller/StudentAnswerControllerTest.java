package com.example.lms.controller;

import com.example.lms.entity.StudentAnswer;
import com.example.lms.service.StudentAnswerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentAnswerController.class)
class StudentAnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentAnswerService studentAnswerService;

    @Autowired
    private ObjectMapper objectMapper;

    private StudentAnswer studentAnswer;

    @BeforeEach
    void setUp() {
        studentAnswer = new StudentAnswer();
        studentAnswer.setId(1L);
        studentAnswer.setSelectedAnswer("Option A");
        studentAnswer.setIsCorrect(true);
    }

    @Test
    void testGetAllAnswers() throws Exception {
        when(studentAnswerService.getAllAnswers()).thenReturn(Arrays.asList(studentAnswer));

        mockMvc.perform(get("/lms/student-answers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(studentAnswer.getId()))
                .andExpect(jsonPath("$[0].selectedAnswer").value("Option A"))
                .andExpect(jsonPath("$[0].isCorrect").value(true));
    }

    @Test
    void testGetAnswerById_Found() throws Exception {
        when(studentAnswerService.getAnswerById(1L)).thenReturn(Optional.of(studentAnswer));

        mockMvc.perform(get("/lms/student-answers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentAnswer.getId()))
                .andExpect(jsonPath("$.selectedAnswer").value("Option A"))
                .andExpect(jsonPath("$.isCorrect").value(true));
    }

    @Test
    void testGetAnswerById_NotFound() throws Exception {
        when(studentAnswerService.getAnswerById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/lms/student-answers/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateAnswer() throws Exception {
        when(studentAnswerService.saveAnswer(any(StudentAnswer.class))).thenReturn(studentAnswer);

        mockMvc.perform(post("/lms/student-answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentAnswer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.selectedAnswer").value("Option A"))
                .andExpect(jsonPath("$.isCorrect").value(true));
    }

    @Test
    void testUpdateAnswer_Found() throws Exception {
        StudentAnswer updated = new StudentAnswer();
        updated.setId(1L);
        updated.setSelectedAnswer("Option B");
        updated.setIsCorrect(false);

        when(studentAnswerService.getAnswerById(1L)).thenReturn(Optional.of(studentAnswer));
        when(studentAnswerService.saveAnswer(any(StudentAnswer.class))).thenReturn(updated);

        mockMvc.perform(put("/lms/student-answers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.selectedAnswer").value("Option B"))
                .andExpect(jsonPath("$.isCorrect").value(false));
    }

    @Test
    void testUpdateAnswer_NotFound() throws Exception {
        when(studentAnswerService.getAnswerById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/lms/student-answers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentAnswer)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAnswer() throws Exception {
        doNothing().when(studentAnswerService).deleteAnswer(1L);

        mockMvc.perform(delete("/lms/student-answers/1"))
                .andExpect(status().isNoContent());
    }
}
