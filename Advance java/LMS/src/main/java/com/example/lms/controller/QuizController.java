package com.example.lms.controller;

import com.example.lms.entity.Quiz;
import com.example.lms.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lms/quizzes")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // Get all quizzes
    @GetMapping
    public List<Quiz> getAllQuizzes() {
        logger.info("Fetching all quizzes");
        return quizService.getAllQuizzes();
    }

    // Get quiz by ID
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        logger.info("Fetching quiz with ID: {}", id);
        return quizService.getQuizById(id)
                .map(quiz -> {
                    logger.info("Quiz found: {}", quiz);
                    return ResponseEntity.ok(quiz);
                })
                .orElseGet(() -> {
                    logger.warn("Quiz with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Get quizzes by instructor
    @GetMapping("/instructor/{instructorId}")
    public List<Quiz> getQuizzesByInstructor(@PathVariable Long instructorId) {
        logger.info("Fetching quizzes for instructor with ID: {}", instructorId);
        return quizService.getQuizzesByInstructor(instructorId);
    }

    // Create new quiz
    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        logger.info("Creating new quiz: {}", quiz);
        return quizService.saveQuiz(quiz);
    }

    // Update quiz
    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        logger.info("Updating quiz with ID: {}", id);
        return quizService.getQuizById(id)
                .map(existing -> {
                    quiz.setQuizId(id);
                    Quiz updated = quizService.saveQuiz(quiz);
                    logger.info("Quiz updated successfully: {}", updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    logger.warn("Quiz with ID {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Delete quiz
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        logger.info("Deleting quiz with ID: {}", id);
        quizService.deleteQuiz(id);
        logger.info("Quiz with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
