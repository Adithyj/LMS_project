package com.example.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grading")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;  
    private String grade;    

    public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public LocalDateTime getGradedAt() {
		return gradedAt;
	}

	public void setGradedAt(LocalDateTime gradedAt) {
		this.gradedAt = gradedAt;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public QuizSubmission getSubmission() {
		return submission;
	}

	public void setSubmission(QuizSubmission submission) {
		this.submission = submission;
	}

	private LocalDateTime gradedAt;

    @ManyToOne
    @JoinColumn(name = "graded_by")
    private Instructor instructor;  

    @OneToOne
    @JoinColumn(name = "submission_id", nullable = false)
    private QuizSubmission submission;

   }
