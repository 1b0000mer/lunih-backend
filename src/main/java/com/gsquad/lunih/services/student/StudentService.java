package com.gsquad.lunih.services.student;

import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.student.ApproveStudentDTO;
import com.gsquad.lunih.entities.Student;

import java.security.Principal;
import java.util.List;

public interface StudentService {

    List<Student> listAll();

    Student get(String studentID);

    Student getCurrent(Principal principal);

    Student update(String studentID, StudentAccountDTO dto);

    Student approveStudent(String studentID, ApproveStudentDTO approveStudentDTO);

    Student delete(String studentID);
}
