package com.example.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.Grade;

public interface GradeRepository  extends JpaRepository<Grade,Long>{
	 Grade findBySubmission_Id(Long submissionId);
}
