package com.example.lms.entity;

import jakarta.persistence.*;

public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer totalMarks;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", title=" + title + ", totalMarks=" + totalMarks + ", course=" + course + "]";
	}

	public Quiz(String title, Integer totalMarks, Course course) {
		super();
		this.title = title;
		this.totalMarks = totalMarks;
		this.course = course;
	}

	public Quiz() {
		// TODO Auto-generated constructor stub
	}

	public void setQuizId(Long id2) {
		this.id = id2;
		
	}

	public Object getQuizId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
    
    
}
