package com.example.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.Course;

public interface CourseRepository extends JpaRepository<Course,Long> {

}
