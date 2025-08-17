package com.example.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor,Long>{

	Instructor findByEmail(String email);
}
