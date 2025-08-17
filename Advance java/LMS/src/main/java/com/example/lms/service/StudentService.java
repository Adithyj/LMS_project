package com.example.lms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.lms.entity.Student;
import com.example.lms.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get all students
    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        List<Student> students = studentRepository.findAll();
        logger.debug("Found {} students", students.size());
        return students;
    }

    // Get a student by ID
    public Optional<Student> getStudentById(Long id) {
        logger.info("Fetching student by ID: {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            logger.debug("Student found with ID: {}", id);
        } else {
            logger.warn("No student found with ID: {}", id);
        }
        return student;
    }

    // Save a new student or update an existing one
    public Student saveStudent(Student student) {
        logger.info("Saving student with email: {}", student.getEmail());
        Student saved = studentRepository.save(student);
        logger.debug("Student saved with ID: {}", saved.getId());
        return saved;
    }

    // Delete a student
    public void deleteStudent(Long id) {
        logger.warn("Deleting student with ID: {}", id);
        studentRepository.deleteById(id);
    }

    // Find student by email (custom method)
    public Optional<Student> getStudentByEmail(String email) {
        logger.info("Fetching student by email: {}", email);
        Optional<Student> student = Optional.ofNullable(studentRepository.findByEmail(email));
        if (student.isPresent()) {
            logger.debug("Student found with email: {}", email);
        } else {
            logger.warn("No student found with email: {}", email);
        }
        return student;
    }
}
