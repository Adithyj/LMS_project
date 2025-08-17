package com.example.lms.service;

import com.example.lms.entity.QuizQuestion;
import com.example.lms.repository.QuizQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizQuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuizQuestionService.class);

    private final QuizQuestionRepository quizQuestionRepository;

    public QuizQuestionService(QuizQuestionRepository quizQuestionRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
    }

    public List<QuizQuestion> getAllQuestions() {
        logger.info("Fetching all quiz questions");
        List<QuizQuestion> questions = quizQuestionRepository.findAll();
        logger.debug("Found {} quiz questions", questions.size());
        return questions;
    }

    public Optional<QuizQuestion> getQuestionById(Long id) {
        logger.info("Fetching quiz question by ID: {}", id);
        Optional<QuizQuestion> question = quizQuestionRepository.findById(id);
        if (question.isPresent()) {
            logger.debug("Quiz question found with ID: {}", id);
        } else {
            logger.warn("No quiz question found with ID: {}", id);
        }
        return question;
    }

    public List<QuizQuestion> getQuestionsByQuiz(Long quizId) {
        logger.info("Fetching questions for quiz ID: {}", quizId);
        List<QuizQuestion> questions = quizQuestionRepository.findByQuiz_QuizId(quizId);
        logger.debug("Found {} questions for quiz ID: {}", questions.size(), quizId);
        return questions;
    }

    public QuizQuestion saveQuestion(QuizQuestion question) {
        logger.info("Saving quiz question: {}", question.getQuestionText());
        QuizQuestion saved = quizQuestionRepository.save(question);
        logger.debug("Quiz question saved with ID: {}", saved.getId());
        return saved;
    }

    public void deleteQuestion(Long id) {
        logger.warn("Deleting quiz question with ID: {}", id);
        quizQuestionRepository.deleteById(id);
    }
}
