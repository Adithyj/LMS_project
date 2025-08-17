package com.example.lms.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;


@Entity

public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enrollmentDate;

    public Enrollment() {
    }

    public Enrollment(Student student, Course course, Date enrollmentDate) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
    }

    public long getEnrollmentId() {
        return id;
    }

    public void setEnrollmentId(long enrollmentId) {
        this.id =  enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + id +
                ", student=" + student +
                ", course=" + course +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }

	public void setId(Long id2) {
		this.id=id2;
		
	}

	public Long getId() {
		return this.id;
	}
}

