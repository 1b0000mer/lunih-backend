package com.gsquad.lunih.services.student;

import com.gsquad.lunih.entities.Student;

import java.util.List;

public interface StudentService {

    List<Student> listAll();

    Student get(String studentID);
}
