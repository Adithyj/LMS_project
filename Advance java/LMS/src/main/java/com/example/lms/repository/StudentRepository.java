package com.example.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {

	Student findByEmail(String email);
}
