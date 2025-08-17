package com.example.lms.entity;

import jakarta.persistence.*;
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", description=" + description + ", instructor=" + instructor
				+ "]";
	}

	public Course(Long id, String title, String description, Instructor instructor) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.instructor = instructor;
	}

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public void setId(long l) {
		this.id = l;
		
	}
    
    
}

