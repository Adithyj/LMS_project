package com.example.lms.controller;

import com.example.lms.entity.QuizQuestion;
import com.example.lms.service.QuizQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lms/quiz-questions")
public class QuizQuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuizQuestionController.class);

    private final QuizQuestionService quizQuestionService;

    public QuizQuestionController(QuizQuestionService quizQuestionService) {
        this.quizQuestionService = quizQuestionService;
    }

    // Get all quiz questions
    @GetMapping
    public List<QuizQuestion> getAllQuestions() {
        logger.info("Fetching all quiz questions");
        return quizQuestionService.getAllQuestions();
    }

    // Get quiz question by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuizQuestion> getQuestionById(@PathVariable Long id) {
        logger.info("Fetching quiz question with id {}", id);
        return quizQuestionService.getQuestionById(id)
                .map(question -> {
                    logger.debug("Found quiz question: {}", question);
                    return ResponseEntity.ok(question);
                })
                .orElseGet(() -> {
                    logger.warn("Quiz question with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Get all questions for a specific quiz
    @GetMapping("/quiz/{quizId}")
    public List<QuizQuestion> getQuestionsByQuiz(@PathVariable Long quizId) {
        logger.info("Fetching all questions for quizId {}", quizId);
        return quizQuestionService.getQuestionsByQuiz(quizId);
    }

    // Create new quiz question
    @PostMapping
    public QuizQuestion createQuestion(@RequestBody QuizQuestion question) {
        logger.info("Creating new quiz question: {}", question);
        QuizQuestion saved = quizQuestionService.saveQuestion(question);
        logger.debug("Created quiz question with id {}", saved.getId());
        return saved;
    }

    // Update quiz question
    @PutMapping("/{id}")
    public ResponseEntity<QuizQuestion> updateQuestion(@PathVariable Long id, @RequestBody QuizQuestion question) {
        logger.info("Updating quiz question with id {}", id);
        return quizQuestionService.getQuestionById(id)
                .map(existing -> {
                    question.setId(id);
                    QuizQuestion updated = quizQuestionService.saveQuestion(question);
                    logger.debug("Updated quiz question: {}", updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    logger.warn("Cannot update. Quiz question with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Delete quiz question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        logger.info("Deleting quiz question with id {}", id);
        quizQuestionService.deleteQuestion(id);
        logger.debug("Deleted quiz question with id {}", id);
        return ResponseEntity.noContent().build();
    }
}
