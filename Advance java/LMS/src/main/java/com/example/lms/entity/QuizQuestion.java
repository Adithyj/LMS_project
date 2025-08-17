package com.example.lms.entity;

import jakarta.persistence.*;

public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer; // "A", "B", "C", "D"

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "QuizQuestion [id=" + id + ", questionText=" + questionText + ", optionA=" + optionA + ", optionB="
				+ optionB + ", optionC=" + optionC + ", optionD=" + optionD + ", correctAnswer=" + correctAnswer
				+ ", quiz=" + quiz + "]";
	}

	public QuizQuestion(String questionText, String optionA, String optionB, String optionC, String optionD,
			String correctAnswer, Quiz quiz) {
		super();
		this.questionText = questionText;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.correctAnswer = correctAnswer;
		this.quiz = quiz;
	}

	public QuizQuestion() {
		// TODO Auto-generated constructor stub
	}
    
}
