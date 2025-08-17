package com.example.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long> {

	 List<QuizQuestion> findByQuiz_QuizId(Long quizId);
}
