package com.example.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.StudentAnswer;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer,Long> {

	List<StudentAnswer> findBySubmission_Id(Long submissionId);
    List<StudentAnswer> findByQuestion_Id(Long questionId);

}
