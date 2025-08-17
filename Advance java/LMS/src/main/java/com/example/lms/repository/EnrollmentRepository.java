package com.example.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
	List<Enrollment> findByStudent_Id(Long studentId);
    List<Enrollment> findByCourse_Id(Long courseId);
}
