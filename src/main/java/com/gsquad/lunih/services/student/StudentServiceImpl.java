package com.gsquad.lunih.services.student;

import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.repos.StudentRepo;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    @Override
    public Student getCurrent(Principal principal) {
        return null;
    }

    @Override
    public Student update(String studentID, StudentAccountDTO dto) {
        Student student = get(studentID);

        student.setStudentID(dto.getStudentID());
        student.setFirstName(dto.getFirstName());
        student.setSurName(dto.getSurName());
        student.setBirthDay(dto.getBirthDay());
        student.setGender(dto.getGender());
        student.setPhoneNumber(dto.getPhoneNumber());

        studentRepo.save(student);
        return student;
    }

    @Override
    public Student delete(String studentID) {
        Student student = get(studentID);

        studentRepo.delete(student);
        return student;
    }
}
