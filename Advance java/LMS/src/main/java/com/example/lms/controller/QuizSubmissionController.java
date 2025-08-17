package com.example.lms.controller;

import com.example.lms.entity.QuizSubmission;
import com.example.lms.service.QuizSubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lms/submissions")
public class QuizSubmissionController {

    private static final Logger logger = LoggerFactory.getLogger(QuizSubmissionController.class);

    private final QuizSubmissionService quizSubmissionService;

    public QuizSubmissionController(QuizSubmissionService quizSubmissionService) {
        this.quizSubmissionService = quizSubmissionService;
    }

    // Get all submissions
    @GetMapping
    public List<QuizSubmission> getAllSubmissions() {
        logger.info("Fetching all quiz submissions");
        return quizSubmissionService.getAllSubmissions();
    }

    // Get submission by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuizSubmission> getSubmissionById(@PathVariable Long id) {
        logger.info("Fetching quiz submission with ID: {}", id);
        return quizSubmissionService.getSubmissionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get submissions by student
    @GetMapping("/student/{studentId}")
    public List<QuizSubmission> getSubmissionsByStudent(@PathVariable Long studentId) {
        logger.info("Fetching submissions for student ID: {}", studentId);
        return quizSubmissionService.getSubmissionsByStudent(studentId);
    }

    // Get submissions by quiz
    @GetMapping("/quiz/{quizId}")
    public List<QuizSubmission> getSubmissionsByQuiz(@PathVariable Long quizId) {
        logger.info("Fetching submissions for quiz ID: {}", quizId);
        return quizSubmissionService.getSubmissionsByQuiz(quizId);
    }

    // Create new submission
    @PostMapping
    public QuizSubmission createSubmission(@RequestBody QuizSubmission submission) {
        logger.info("Creating new quiz submission for student ID: {}", submission.getStudent().getId());
        return quizSubmissionService.saveSubmission(submission);
    }

    // Update submission (e.g., add score or endTime)
    @PutMapping("/{id}")
    public ResponseEntity<QuizSubmission> updateSubmission(@PathVariable Long id, @RequestBody QuizSubmission submission) {
        logger.info("Updating quiz submission with ID: {}", id);
        return quizSubmissionService.getSubmissionById(id)
                .map(existing -> {
                    submission.setId(id);
                    return ResponseEntity.ok(quizSubmissionService.saveSubmission(submission));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete submission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        logger.warn("Deleting quiz submission with ID: {}", id);
        quizSubmissionService.deleteSubmission(id);
        return ResponseEntity.noContent().build();
    }
}
