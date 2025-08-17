package com.example.lms.security;

import com.example.lms.entity.Student;
import com.example.lms.repository.StudentRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class StudentDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public StudentDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new UsernameNotFoundException("Student not found");
        }
        return new User(
                student.getEmail(),
                student.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + student.getRole()))
        );
    }
}
