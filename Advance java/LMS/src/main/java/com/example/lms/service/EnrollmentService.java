package com.example.lms.service;

import com.example.lms.entity.Enrollment;
import com.example.lms.repository.EnrollmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getAllEnrollments() {
        logger.info("Fetching all enrollments");
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        logger.debug("Found {} enrollments", enrollments.size());
        return enrollments;
    }

    public Optional<Enrollment> getEnrollmentById(Long id) {
        logger.info("Fetching enrollment by ID: {}", id);
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        logger.info("Fetching enrollments for student ID: {}", studentId);
        List<Enrollment> enrollments = enrollmentRepository.findByStudent_Id(studentId);
        logger.debug("Found {} enrollments for student ID: {}", enrollments.size(), studentId);
        return enrollments;
    }

    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        logger.info("Fetching enrollments for course ID: {}", courseId);
        List<Enrollment> enrollments = enrollmentRepository.findByCourse_Id(courseId);
        logger.debug("Found {} enrollments for course ID: {}", enrollments.size(), courseId);
        return enrollments;
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        logger.info("Saving enrollment for student ID: {} in course ID: {}",
                enrollment.getStudent() != null ? enrollment.getStudent().getId() : null,
                enrollment.getCourse() != null ? enrollment.getCourse().getId() : null);
        Enrollment saved = enrollmentRepository.save(enrollment);
        logger.debug("Enrollment saved with ID: {}", saved.getId());
        return saved;
    }

    public void deleteEnrollment(Long id) {
        logger.warn("Deleting enrollment with ID: {}", id);
        enrollmentRepository.deleteById(id);
    }
}
