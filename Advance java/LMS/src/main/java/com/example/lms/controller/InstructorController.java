package com.example.lms.controller;

import com.example.lms.entity.Instructor;
import com.example.lms.service.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lms/instructors")
public class InstructorController {

    private static final Logger log = LoggerFactory.getLogger(InstructorController.class);

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    // Get all instructors
    @GetMapping
    public List<Instructor> getAllInstructors() {
        log.info("Fetching all instructors");
        return instructorService.getAllInstructors();
    }

    // Get instructor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long id) {
        log.info("Fetching instructor with id {}", id);
        return instructorService.getInstructorById(id)
                .map(instructor -> {
                    log.debug("Instructor found: {}", instructor);
                    return ResponseEntity.ok(instructor);
                })
                .orElseGet(() -> {
                    log.warn("Instructor with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Create new instructor
    @PostMapping
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        log.info("Creating new instructor with email {}", instructor.getEmail());
        return instructorService.saveInstructor(instructor);
    }

    // Update instructor
    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody Instructor instructor) {
        log.info("Updating instructor with id {}", id);
        return instructorService.getInstructorById(id)
                .map(existing -> {
                    instructor.setInstructorId(id); // ensure correct update
                    log.debug("Instructor updated: {}", instructor);
                    return ResponseEntity.ok(instructorService.saveInstructor(instructor));
                })
                .orElseGet(() -> {
                    log.warn("Instructor with id {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Delete instructor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        log.info("Deleting instructor with id {}", id);
        instructorService.deleteInstructor(id);
        log.debug("Instructor with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    // Get instructor by email
    @GetMapping("/search")
    public ResponseEntity<Instructor> getInstructorByEmail(@RequestParam String email) {
        log.info("Searching instructor by email {}", email);
        return instructorService.getInstructorByEmail(email)
                .map(instructor -> {
                    log.debug("Instructor found: {}", instructor);
                    return ResponseEntity.ok(instructor);
                })
                .orElseGet(() -> {
                    log.warn("Instructor with email {} not found", email);
                    return ResponseEntity.notFound().build();
                });
    }
}
