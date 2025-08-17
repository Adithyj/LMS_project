package com.example.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.QuizSubmission;

public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission,Long>{

	 List<QuizSubmission> findByStudent_Id(Long studentId);
	    List<QuizSubmission> findByQuiz_Id(Long quizId);
}
