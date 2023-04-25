package com.gsquad.lunih.services.student;

import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.student.ApproveStudentDTO;
import com.gsquad.lunih.entities.Student;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface StudentService {

    Page<Student> listAllPaging(String search, int page, int size, String sort, String column);

    List<Student> listAll();

    Student get(String studentID);

    Student getCurrent(Principal principal);

    Student update(String studentID, StudentAccountDTO dto);

    Student approveStudent(String studentID, ApproveStudentDTO approveStudentDTO);

    Student delete(String studentID);

}
