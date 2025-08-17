package com.example.lms.controller;

import com.example.lms.entity.Grade;
import com.example.lms.service.GradeService;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lms/grades")
public class GradeController {

    private final GradeService gradeService;
    private static final Logger log = LoggerFactory.getLogger(GradeController.class);
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public List<Grade> getAllGrades() {
        log.info("Fetching all grades");
        return gradeService.getAllGrades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Long id) {
        log.info("Fetching grade with ID: {}", id);
        return gradeService.getGradeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Grade not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/submission/{submissionId}")
    public Grade getGradeBySubmission(@PathVariable Long submissionId) {
        log.info("Fetching grade for submission ID: {}", submissionId);
        return gradeService.getGradeBySubmission(submissionId);
    }

    @PostMapping
    public Grade createGrade(@RequestBody Grade grade) {
        log.info("Creating new grade for submission ID: {}", 
                 grade.getSubmission() != null ? grade.getSubmission().getId() : "null");
        return gradeService.saveGrade(grade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade) {
        log.info("Updating grade with ID: {}", id);
        return gradeService.getGradeById(id)
                .map(existing -> {
                    grade.setId(id);
                    Grade updated = gradeService.saveGrade(grade);
                    log.info("Grade updated with ID: {}", id);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    log.warn("Grade not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        log.warn("Deleting grade with ID: {}", id);
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}
