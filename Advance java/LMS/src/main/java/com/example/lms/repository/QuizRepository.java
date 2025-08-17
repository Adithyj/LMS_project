package com.example.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

	List<Quiz> findByInstructor(Long instructorId);

}
