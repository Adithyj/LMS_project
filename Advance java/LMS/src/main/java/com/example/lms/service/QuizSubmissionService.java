package com.example.lms.service;

import com.example.lms.entity.QuizSubmission;
import com.example.lms.repository.QuizSubmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizSubmissionService {

    private static final Logger logger = LoggerFactory.getLogger(QuizSubmissionService.class);

    private final QuizSubmissionRepository quizSubmissionRepository;

    public QuizSubmissionService(QuizSubmissionRepository quizSubmissionRepository) {
        this.quizSubmissionRepository = quizSubmissionRepository;
    }

    public List<QuizSubmission> getAllSubmissions() {
        logger.info("Fetching all quiz submissions");
        List<QuizSubmission> submissions = quizSubmissionRepository.findAll();
        logger.debug("Found {} quiz submissions", submissions.size());
        return submissions;
    }

    public Optional<QuizSubmission> getSubmissionById(Long id) {
        logger.info("Fetching quiz submission by ID: {}", id);
        Optional<QuizSubmission> submission = quizSubmissionRepository.findById(id);
        if (submission.isPresent()) {
            logger.debug("Quiz submission found with ID: {}", id);
        } else {
            logger.warn("No quiz submission found with ID: {}", id);
        }
        return submission;
    }

    public List<QuizSubmission> getSubmissionsByStudent(Long studentId) {
        logger.info("Fetching quiz submissions for student ID: {}", studentId);
        List<QuizSubmission> submissions = quizSubmissionRepository.findByStudent_Id(studentId);
        logger.debug("Found {} submissions for student ID: {}", submissions.size(), studentId);
        return submissions;
    }

    public List<QuizSubmission> getSubmissionsByQuiz(Long quizId) {
        logger.info("Fetching quiz submissions for quiz ID: {}", quizId);
        List<QuizSubmission> submissions = quizSubmissionRepository.findByQuiz_Id(quizId);
        logger.debug("Found {} submissions for quiz ID: {}", submissions.size(), quizId);
        return submissions;
    }

    public QuizSubmission saveSubmission(QuizSubmission submission) {
        logger.info("Saving quiz submission for student ID: {}, quiz ID: {}", 
                    submission.getStudent().getId(), submission.getQuiz().getId());
        QuizSubmission saved = quizSubmissionRepository.save(submission);
        logger.debug("Quiz submission saved with ID: {}", saved.getId());
        return saved;
    }

    public void deleteSubmission(Long id) {
        logger.warn("Deleting quiz submission with ID: {}", id);
        quizSubmissionRepository.deleteById(id);
    }
}
