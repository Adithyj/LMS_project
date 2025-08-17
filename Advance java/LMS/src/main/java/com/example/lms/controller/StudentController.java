package com.example.lms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.lms.entity.Student;
import com.example.lms.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/lms/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        return studentService.getAllStudents();
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        logger.info("Fetching student with id: {}", id);
        return studentService.getStudentById(id)
                .map(student -> {
                    logger.debug("Found student: {}", student);
                    return ResponseEntity.ok(student);
                })
                .orElseGet(() -> {
                    logger.warn("Student with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Create new student
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        logger.info("Creating new student: {}", student);
        Student savedStudent = studentService.saveStudent(student);
        logger.debug("Saved student: {}", savedStudent);
        return savedStudent;
    }

    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        logger.info("Updating student with id: {}", id);
        return studentService.getStudentById(id)
                .map(existing -> {
                    student.setId(id); // ensure update
                    Student updated = studentService.saveStudent(student);
                    logger.debug("Updated student: {}", updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    logger.warn("Cannot update, student with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        logger.info("Deleting student with id: {}", id);
        studentService.deleteStudent(id);
        logger.debug("Deleted student with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    // Get student by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        logger.info("Fetching student by email: {}", email);
        return studentService.getStudentByEmail(email)
                .map(student -> {
                    logger.debug("Found student: {}", student);
                    return ResponseEntity.ok(student);
                })
                .orElseGet(() -> {
                    logger.warn("Student with email {} not found", email);
                    return ResponseEntity.notFound().build();
                });
    }
}
