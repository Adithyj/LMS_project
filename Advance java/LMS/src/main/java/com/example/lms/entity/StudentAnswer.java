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
}
