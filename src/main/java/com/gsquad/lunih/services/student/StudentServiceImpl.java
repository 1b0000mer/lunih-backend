package com.gsquad.lunih.services.student;

import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.student.ApproveStudentDTO;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.StudentRepo;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final MessageSource messageSource;

    public StudentServiceImpl(StudentRepo studentRepo, MessageSource messageSource) {
        this.studentRepo = studentRepo;
        this.messageSource = messageSource;
    }

    @Override
    public List<Student> listAll() {
        return studentRepo.findAll();
    }

    @Override
    public Student get(String studentID) {
        Locale locale = LocaleContextHolder.getLocale();

        return studentRepo.findById(studentID).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.student.studentID-notfound", null, locale), studentID)));
    }

    @Override
    public Student getCurrent(Principal principal) {
        return null;
    }

    @Override
    public Student update(String studentID, StudentAccountDTO dto) {
        Locale locale = LocaleContextHolder.getLocale();

        Student student = get(studentID);

//        if (ObjectUtils.isEmpty(dto.getStudentID())) {
//            throw new InvalidException(messageSource.getMessage("error.student.studentID-empty", null, locale));
//        }

        if (ObjectUtils.isEmpty(dto.getFirstName())) {
            throw new InvalidException(messageSource.getMessage("error.student.firstName-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getSurName())) {
            throw new InvalidException(messageSource.getMessage("error.student.surName-empty", null, locale));
        }

//        //check exist
//        if (studentRepo.existsById(dto.getStudentID())) {
//            throw new InvalidException(String.format(messageSource.getMessage("error.student.studentID-exist", null, locale), dto.getStudentID()));
//        }

//        student.setStudentID(dto.getStudentID());
        student.setFirstName(dto.getFirstName());
        student.setSurName(dto.getSurName());

        if (!ObjectUtils.isEmpty(dto.getBirthDay())) {
            student.setBirthDay(dto.getBirthDay());
        }

        if (!ObjectUtils.isEmpty(dto.getGender())) {
            student.setGender(dto.getGender());
        }

        if (!ObjectUtils.isEmpty(dto.getPhoneNumber())) {
            student.setPhoneNumber(dto.getPhoneNumber());
        }

//        //if change email
//        if (!dto.getEmail().equals(student.getAccount().getEmail())) {
//            student.getAccount().setEmail(dto.getEmail());
//        }
//
//        //if change password
//        if (!dto.getPassword().equals(student.getAccount().getPassword())) {
//            student.getAccount().setPassword(dto.getPassword());
//        }

        studentRepo.save(student);
        return student;
    }

    @Override
    public Student approveStudent(String studentID, ApproveStudentDTO approveStudentDTO) {
        Locale locale = LocaleContextHolder.getLocale();

        Student student = get(studentID);

        student.setApproved(approveStudentDTO.getIsApproved());
        if (!approveStudentDTO.getIsApproved()) {

            if (ObjectUtils.isEmpty(approveStudentDTO.getReason())) {
                throw new InvalidException(messageSource.getMessage("error.approve.reason-empty", null, locale));
            }
            student.setReason(approveStudentDTO.getReason());
            // TODO send mail
        }

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
