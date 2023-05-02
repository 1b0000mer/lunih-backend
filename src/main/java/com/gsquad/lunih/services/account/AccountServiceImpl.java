package com.gsquad.lunih.services.account;

import com.gsquad.lunih.dtos.accountDTO.*;
import com.gsquad.lunih.entities.Account;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.entities.University;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.AccountRepo;
import com.gsquad.lunih.repos.StudentRepo;
import com.gsquad.lunih.repos.UniversityRepo;
import com.gsquad.lunih.utils.EnumRole;
import com.gsquad.lunih.utils.PageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final StudentRepo studentRepo;
    private final UniversityRepo universityRepo;
    private final MessageSource messageSource;

    public AccountServiceImpl(AccountRepo accountRepo, StudentRepo studentRepo, UniversityRepo universityRepo, MessageSource messageSource) {
        this.accountRepo = accountRepo;
        this.studentRepo = studentRepo;
        this.universityRepo = universityRepo;
        this.messageSource = messageSource;
    }

    @Override
    public Page<Account> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

        return accountRepo.getAllAccountPaging(search, pageable);
    }

    @Override
    public List<Account> listAll() {
        return accountRepo.findAll();
    }

    @Override
    public Account get(int id) {
        Locale locale = LocaleContextHolder.getLocale();

        return accountRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.account.id-notfound", null, locale), id)));
    }

    @Override
    public Account createNewStudent(StudentAccountDTO dto) {
        Locale locale = LocaleContextHolder.getLocale();

        if (ObjectUtils.isEmpty(dto.getEmail())) {
            throw new InvalidException(messageSource.getMessage("error.account.email-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getPassword())) {
            throw new InvalidException(messageSource.getMessage("error.account.password-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getStudentID())) {
            throw new InvalidException(messageSource.getMessage("error.student.studentID-empty", null, locale));
        }

        //check exist
        if (studentRepo.existsById(dto.getStudentID())) {
            throw new InvalidException(String.format(messageSource.getMessage("error.student.studentID-exist", null, locale), dto.getStudentID()));
        }

        if (ObjectUtils.isEmpty(dto.getFirstName())) {
            throw new InvalidException(messageSource.getMessage("error.student.firstName-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getSurName())) {
            throw new InvalidException(messageSource.getMessage("error.student.surName-empty", null, locale));
        }

        Account acc = new Account();
        acc.setEmail(dto.getEmail());
        acc.setPassword(dto.getPassword());
        acc.setRole(EnumRole.ROLE_STUDENT.name());
        accountRepo.save(acc);

        Student student = new Student();
        student.setStudentID(dto.getStudentID());
        student.setAccount(acc);
        student.setFirstName(dto.getFirstName());
        student.setSurName(dto.getSurName());

        //nullable
        if (!ObjectUtils.isEmpty(dto.getBirthDay())) {
            student.setBirthDay(dto.getBirthDay());
        }

        if (!ObjectUtils.isEmpty(dto.getGender())) {
            student.setGender(dto.getGender());
        }

        if (!ObjectUtils.isEmpty(dto.getPhoneNumber())) {
            student.setPhoneNumber(dto.getPhoneNumber());
        }

        student.setApproved(null);
        studentRepo.save(student);

        return acc;
    }

    @Override
    public Account createNewCompany(CompanyAccountDTO dto) {

        return null;
    }

    @Override
    public Account createNewUniversity(UniversityAccountDTO dto) {
        Locale locale = LocaleContextHolder.getLocale();

        if (ObjectUtils.isEmpty(dto.getEmail())) {
            throw new InvalidException(messageSource.getMessage("error.account.email-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getPassword())) {
            throw new InvalidException(messageSource.getMessage("error.account.password-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException(messageSource.getMessage("error.university.name-empty", null, locale));
        }

        Account acc = new Account();
        acc.setEmail(dto.getEmail());
        acc.setPassword(dto.getPassword());
        acc.setRole(EnumRole.ROLE_UNIVERSITY.name());
        accountRepo.save(acc);

        University university = new University();
        university.setAccount(acc);
        university.setName(dto.getName());

        if (!ObjectUtils.isEmpty(dto.getAddress())) {
            university.setAddress(dto.getAddress());
        }

        if (!ObjectUtils.isEmpty(dto.getPhoneNumber())) {
            university.setPhoneNumber(dto.getPhoneNumber());
        }

        universityRepo.save(university);
        return acc;
    }

//    @Override
//    public Account createNewAdmin(AdminAccountDTO dto) {
//        Locale locale = LocaleContextHolder.getLocale();
//
//        if (ObjectUtils.isEmpty(dto.getEmail())) {
//            throw new InvalidException(messageSource.getMessage("error.account.email-empty", null, locale));
//        }
//
//        if (ObjectUtils.isEmpty(dto.getPassword())) {
//            throw new InvalidException(messageSource.getMessage("error.account.password-empty", null, locale));
//        }
//
//        Account acc = new Account();
//        acc.setEmail(dto.getEmail());
//        acc.setPassword(dto.getPassword());
//        acc.setRole(EnumRole.ROLE_ADMIN.name());
//        accountRepo.save(acc);
//
//        return acc;
//    }

    @Override
    public Account changePassword(int id, ChangePasswordDTO changePasswordDTO) {
        return null;
    }
}
