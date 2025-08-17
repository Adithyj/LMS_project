package com.example.lms.service;

import com.example.lms.entity.StudentAnswer;
import com.example.lms.repository.StudentAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentAnswerService {

    private static final Logger logger = LoggerFactory.getLogger(StudentAnswerService.class);

    private final StudentAnswerRepository studentAnswerRepository;

    public StudentAnswerService(StudentAnswerRepository studentAnswerRepository) {
        this.studentAnswerRepository = studentAnswerRepository;
    }

    public List<StudentAnswer> getAllAnswers() {
        logger.info("Fetching all student answers");
        List<StudentAnswer> answers = studentAnswerRepository.findAll();
        logger.debug("Found {} student answers", answers.size());
        return answers;
    }

    public Optional<StudentAnswer> getAnswerById(Long id) {
        logger.info("Fetching student answer by ID: {}", id);
        Optional<StudentAnswer> answer = studentAnswerRepository.findById(id);
        if (answer.isPresent()) {
            logger.debug("Student answer found with ID: {}", id);
        } else {
            logger.warn("No student answer found with ID: {}", id);
        }
        return answer;
    }

    public List<StudentAnswer> getAnswersBySubmission(Long submissionId) {
        logger.info("Fetching student answers for submission ID: {}", submissionId);
        List<StudentAnswer> answers = studentAnswerRepository.findBySubmission_Id(submissionId);
        logger.debug("Found {} answers for submission ID: {}", answers.size(), submissionId);
        return answers;
    }

    public List<StudentAnswer> getAnswersByQuestion(Long questionId) {
        logger.info("Fetching student answers for question ID: {}", questionId);
        List<StudentAnswer> answers = studentAnswerRepository.findByQuestion_Id(questionId);
        logger.debug("Found {} answers for question ID: {}", answers.size(), questionId);
        return answers;
    }

    public StudentAnswer saveAnswer(StudentAnswer answer) {
        logger.info("Saving student answer for submission ID: {}, question ID: {}",
                answer.getSubmission().getId(), answer.getQuestion().getId());
        StudentAnswer saved = studentAnswerRepository.save(answer);
        logger.debug("Student answer saved with ID: {}", saved.getId());
        return saved;
    }

    public void deleteAnswer(Long id) {
        logger.warn("Deleting student answer with ID: {}", id);
        studentAnswerRepository.deleteById(id);
    }
}
