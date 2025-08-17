package com.example.lms.service;

import com.example.lms.entity.Instructor;
import com.example.lms.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private static final Logger logger = LoggerFactory.getLogger(InstructorService.class);

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> getAllInstructors() {
        logger.info("Fetching all instructors");
        List<Instructor> instructors = instructorRepository.findAll();
        logger.debug("Found {} instructors", instructors.size());
        return instructors;
    }

    public Optional<Instructor> getInstructorById(Long id) {
        logger.info("Fetching instructor by ID: {}", id);
        return instructorRepository.findById(id);
    }

    public Optional<Instructor> getInstructorByEmail(String email) {
        logger.info("Fetching instructor by email: {}", email);
        Optional<Instructor> instructor = Optional.ofNullable(instructorRepository.findByEmail(email));
        if (instructor.isPresent()) {
            logger.debug("Instructor found with email: {}", email);
        } else {
            logger.warn("No instructor found with email: {}", email);
        }
        return instructor;
    }

    public Instructor saveInstructor(Instructor instructor) {
        logger.info("Saving instructor with name: {} and email: {}",
                instructor.getName(), instructor.getEmail());
        Instructor saved = instructorRepository.save(instructor);
        logger.debug("Instructor saved with ID: {}", saved.getId());
        return saved;
    }

    public void deleteInstructor(Long id) {
        logger.warn("Deleting instructor with ID: {}", id);
        instructorRepository.deleteById(id);
    }
}
