package com.example.lms.entity;

import jakarta.persistence.*;


@Entity


public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Instructor [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
	public Instructor(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	public Instructor() {
		// TODO Auto-generated constructor stub
	}
	public void setInstructorId(Long id2) {
		this.id = id2;
		
	}
    
}