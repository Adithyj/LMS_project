package com.example.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.lms.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

	@Query("SELECT q FROM Quiz q WHERE q.instructor.id = :instructorId")
	List<Quiz> findByInstructorId(@Param("instructorId") Long instructorId);


}
