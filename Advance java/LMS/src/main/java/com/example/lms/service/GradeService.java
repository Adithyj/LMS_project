package com.example.lms.service;

import com.example.lms.entity.Grade;
import com.example.lms.repository.GradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    private static final Logger logger = LoggerFactory.getLogger(GradeService.class);

    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getAllGrades() {
        logger.info("Fetching all grades");
        List<Grade> grades = gradeRepository.findAll();
        logger.debug("Found {} grades", grades.size());
        return grades;
    }

    public Optional<Grade> getGradeById(Long id) {
        logger.info("Fetching grade by ID: {}", id);
        return gradeRepository.findById(id);
    }

    public Grade getGradeBySubmission(Long submissionId) {
        logger.info("Fetching grade for submission ID: {}", submissionId);
        Grade grade = gradeRepository.findBySubmission_Id(submissionId);
        if (grade != null) {
            logger.debug("Found grade with ID: {} for submission ID: {}", grade.getId(), submissionId);
        } else {
            logger.warn("No grade found for submission ID: {}", submissionId);
        }
        return grade;
    }

    public Grade saveGrade(Grade grade) {
        logger.info("Saving grade for submission ID: {} with score: {}",
                grade.getSubmission() != null ? grade.getSubmission().getId() : null,
                grade.getScore());
        Grade saved = gradeRepository.save(grade);
        logger.debug("Grade saved with ID: {}", saved.getId());
        return saved;
    }

    public void deleteGrade(Long id) {
        logger.warn("Deleting grade with ID: {}", id);
        gradeRepository.deleteById(id);
    }
}
