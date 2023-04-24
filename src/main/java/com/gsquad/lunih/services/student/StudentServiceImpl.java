package com.gsquad.lunih.services.student;

import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.repos.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public List<Student> listAll() {
        return studentRepo.findAll();
    }

    @Override
    public Student get(String studentID) {
        return studentRepo.findById(studentID).get();
    }
}
