package com.example.lms.controller;

import com.example.lms.entity.StudentAnswer;
import com.example.lms.service.StudentAnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lms/student-answers")
public class StudentAnswerController {

    private static final Logger logger = LoggerFactory.getLogger(StudentAnswerController.class);

    private final StudentAnswerService studentAnswerService;

    public StudentAnswerController(StudentAnswerService studentAnswerService) {
        this.studentAnswerService = studentAnswerService;
    }

    // Get all answers
    @GetMapping
    public List<StudentAnswer> getAllAnswers() {
        logger.info("Fetching all student answers");
        return studentAnswerService.getAllAnswers();
    }

    // Get answer by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentAnswer> getAnswerById(@PathVariable Long id) {
        logger.info("Fetching student answer with id: {}", id);
        return studentAnswerService.getAnswerById(id)
                .map(answer -> {
                    logger.debug("Found student answer: {}", answer);
                    return ResponseEntity.ok(answer);
                })
                .orElseGet(() -> {
                    logger.warn("Student answer with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Get all answers for a specific submission
    @GetMapping("/submission/{submissionId}")
    public List<StudentAnswer> getAnswersBySubmission(@PathVariable Long submissionId) {
        logger.info("Fetching answers for submissionId: {}", submissionId);
        return studentAnswerService.getAnswersBySubmission(submissionId);
    }

    // Get all answers for a specific question
    @GetMapping("/question/{questionId}")
    public List<StudentAnswer> getAnswersByQuestion(@PathVariable Long questionId) {
        logger.info("Fetching answers for questionId: {}", questionId);
        return studentAnswerService.getAnswersByQuestion(questionId);
    }

    // Create a new answer
    @PostMapping
    public ResponseEntity<StudentAnswer> createAnswer(@RequestBody StudentAnswer answer) {
        logger.info("Creating new student answer: {}", answer);
        StudentAnswer savedAnswer = studentAnswerService.saveAnswer(answer);
        logger.debug("Saved student answer: {}", savedAnswer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnswer);
    }

    // Update answer
    @PutMapping("/{id}")
    public ResponseEntity<StudentAnswer> updateAnswer(@PathVariable Long id, @RequestBody StudentAnswer answer) {
        logger.info("Updating student answer with id: {}", id);
        return studentAnswerService.getAnswerById(id)
                .map(existing -> {
                    answer.setId(id);
                    StudentAnswer updated = studentAnswerService.saveAnswer(answer);
                    logger.debug("Updated student answer: {}", updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    logger.warn("Cannot update, student answer with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Delete answer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        logger.info("Deleting student answer with id: {}", id);
        studentAnswerService.deleteAnswer(id);
        logger.debug("Deleted student answer with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
