package com.gsquad.lunih.services.student;

import com.gsquad.lunih.dtos.UploadFileDTO;
import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.student.ApproveStudentDTO;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.StudentRepo;
import com.gsquad.lunih.services.program.ProgramService;
import com.gsquad.lunih.utils.PageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    private final ProgramService programService;
    private final MessageSource messageSource;

    public StudentServiceImpl(StudentRepo studentRepo, ProgramService programService, MessageSource messageSource) {
        this.studentRepo = studentRepo;
        this.programService = programService;
        this.messageSource = messageSource;
    }

    @Override
    public Page<Student> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

        return studentRepo.getAllStudentPaging(search, pageable);
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
        Locale locale = LocaleContextHolder.getLocale();
        return studentRepo.findByAccount_Email(principal.getName())
                .orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.student.email-notfound", null, locale), principal.getName())));
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

        if (dto.getProgram() != -1) {
            student.setProgram(programService.get(dto.getProgram()));
        }

        student.setBirthDay(dto.getBirthDay());
        student.setGender(dto.getGender());
        student.setPhoneNumber(dto.getPhoneNumber());

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
            // TODO: send mail
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

    @Override
    public Student uploadCertification(Principal principal, UploadFileDTO dto) {
        Student student = getCurrent(principal);
        student.setFileCertification(dto.getIdFile());
        studentRepo.save(student);
        return student;
    }
}
