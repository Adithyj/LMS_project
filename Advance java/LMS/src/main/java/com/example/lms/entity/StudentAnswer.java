package com.example.lms.entity;

import jakarta.persistence.*;


@Entity

@Table(name = "student_answers")
public class StudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String selectedAnswer; // e.g., "A", "B", "C", "D"

    private Boolean isCorrect; // true if matches question.correctAnswer

    // The question this answer belongs to
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestion question;

    // The quiz submission (attempt) this answer is part of
    @ManyToOne
    @JoinColumn(name = "submission_id", nullable = false)
    private QuizSubmission submission;

	public void setId(Long id2) {
		this.id =id2;
	}

	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public QuizQuestion getQuestion() {
		return question;
	}

	public void setQuestion(QuizQuestion question) {
		this.question = question;
	}

	public QuizSubmission getSubmission() {
		return submission;
	}

	public void setSubmission(QuizSubmission submission) {
		this.submission = submission;
	}

	public Long getId() {
		return id;
	}
}
