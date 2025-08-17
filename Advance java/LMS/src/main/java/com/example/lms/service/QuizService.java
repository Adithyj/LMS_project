package com.example.lms.service;

import com.example.lms.entity.Quiz;
import com.example.lms.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getAllQuizzes() {
        logger.info("Fetching all quizzes");
        List<Quiz> quizzes = quizRepository.findAll();
        logger.debug("Found {} quizzes", quizzes.size());
        return quizzes;
    }

    public Optional<Quiz> getQuizById(Long id) {
        logger.info("Fetching quiz by ID: {}", id);
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            logger.debug("Quiz found with ID: {}", id);
        } else {
            logger.warn("No quiz found with ID: {}", id);
        }
        return quiz;
    }

    public List<Quiz> getQuizzesByInstructor(Long instructorId) {
        logger.info("Fetching quizzes for instructor ID: {}", instructorId);
        List<Quiz> quizzes = quizRepository.findByInstructor(instructorId);
        logger.debug("Found {} quizzes for instructor ID: {}", quizzes.size(), instructorId);
        return quizzes;
    }

    public Quiz saveQuiz(Quiz quiz) {
        logger.info("Saving quiz: {}", quiz.getTitle());
        Quiz saved = quizRepository.save(quiz);
        logger.debug("Quiz saved with ID: {}", saved.getQuizId());
        return saved;
    }

    public void deleteQuiz(Long id) {
        logger.warn("Deleting quiz with ID: {}", id);
        quizRepository.deleteById(id);
    }
}
