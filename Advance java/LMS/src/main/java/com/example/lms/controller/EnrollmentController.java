package com.example.lms.controller;

import com.example.lms.entity.Enrollment;
import com.example.lms.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lms/enrollments")
@Slf4j
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private static final Logger log = LoggerFactory.getLogger(EnrollmentController.class);
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        log.info("Fetching all enrollments");
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        log.debug("Found {} enrollments", enrollments.size());
        return enrollments;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
        log.info("Fetching enrollment with ID: {}", id);
        return enrollmentService.getEnrollmentById(id)
                .map(enrollment -> {
                    log.debug("Enrollment found: {}", enrollment);
                    return ResponseEntity.ok(enrollment);
                })
                .orElseGet(() -> {
                    log.warn("Enrollment with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
        log.info("Fetching enrollments for student ID: {}", studentId);
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
        log.debug("Found {} enrollments for student ID {}", enrollments.size(), studentId);
        return enrollments;
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long courseId) {
        log.info("Fetching enrollments for course ID: {}", courseId);
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
        log.debug("Found {} enrollments for course ID {}", enrollments.size(), courseId);
        return enrollments;
    }

    @PostMapping
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) {
        log.info("Creating new enrollment");
        log.debug("Enrollment request body: {}", enrollment);
        Enrollment saved = enrollmentService.saveEnrollment(enrollment);
        log.info("Enrollment created with ID: {}", saved.getId());
        return saved;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        log.info("Updating enrollment with ID: {}", id);
        return enrollmentService.getEnrollmentById(id)
                .map(existing -> {
                    enrollment.setId(id);
                    Enrollment updated = enrollmentService.saveEnrollment(enrollment);
                    log.info("Enrollment with ID {} updated successfully", id);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    log.warn("Enrollment with ID {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        log.info("Deleting enrollment with ID: {}", id);
        try {
            enrollmentService.deleteEnrollment(id);
            log.info("Enrollment with ID {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting enrollment with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
