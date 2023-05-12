package com.gsquad.lunih.services.account;

import com.gsquad.lunih.dtos.accountDTO.*;
import com.gsquad.lunih.entities.Account;
import com.gsquad.lunih.entities.Company;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.entities.University;
import com.gsquad.lunih.entities.categories.Program;
import com.gsquad.lunih.entities.categories.Industry;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.AccountRepo;
import com.gsquad.lunih.repos.CompanyRepo;
import com.gsquad.lunih.repos.StudentRepo;
import com.gsquad.lunih.repos.UniversityRepo;
import com.gsquad.lunih.services.program.ProgramService;
import com.gsquad.lunih.services.industry.IndustryService;
import com.gsquad.lunih.utils.EnumRole;
import com.gsquad.lunih.utils.PageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final StudentRepo studentRepo;
    private final ProgramService programService;
    private final UniversityRepo universityRepo;
    private final IndustryService industryService;
    private final CompanyRepo companyRepo;
    private final MessageSource messageSource;

    public AccountServiceImpl(AccountRepo accountRepo, StudentRepo studentRepo, UniversityRepo universityRepo, ProgramService programService, IndustryService industryService, CompanyRepo companyRepo, MessageSource messageSource) {
        this.accountRepo = accountRepo;
        this.studentRepo = studentRepo;
        this.programService = programService;
        this.universityRepo = universityRepo;
        this.industryService = industryService;
        this.companyRepo = companyRepo;
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
        student.setProgram(programService.get(dto.getProgram()));
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
        Locale locale = LocaleContextHolder.getLocale();

        if (ObjectUtils.isEmpty(dto.getEmail())) {
            throw new InvalidException(messageSource.getMessage("error.account.email-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getPassword())) {
            throw new InvalidException(messageSource.getMessage("error.account.password-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getCompanyName())) {
            throw new InvalidException(messageSource.getMessage("error.company.name-empty", null, locale));
        }

        Account acc = new Account();
        acc.setEmail(dto.getEmail());
        acc.setPassword(dto.getPassword());
        acc.setRole(EnumRole.ROLE_COMPANY.name());
        accountRepo.save(acc);

        Company company = new Company();
        company.setAccount(acc);
        company.setCompanyName(dto.getCompanyName());

        if (!ObjectUtils.isEmpty(dto.getCompanyDescription())) {
            company.setCompanyDescription(dto.getCompanyDescription());
        }

        if (!ObjectUtils.isEmpty(dto.getCompanyType())) {
            company.setCompanyType(dto.getCompanyType());
        }

        List<Industry> industryList = new ArrayList<>();
        dto.getIndustryList().forEach(industryID -> industryList.add(industryService.get(industryID)));
        company.setIndustryList(industryList);

        if (!ObjectUtils.isEmpty(dto.getCompanyAddress())) {
            company.setCompanyAddress(dto.getCompanyAddress());
        }

        if (!ObjectUtils.isEmpty(dto.getCompanyWebsite())) {
            company.setCompanyWebsite(dto.getCompanyWebsite());
        }

        if (!ObjectUtils.isEmpty(dto.getCompanyLogo())) {
            company.setCompanyLogo(dto.getCompanyLogo());
        }

        if (ObjectUtils.isEmpty(dto.getCompanyContactPersonName())) {
            throw new InvalidException(messageSource.getMessage("error.company.personname-empty", null, locale));
        }

        company.setCompanyContactPersonName(dto.getCompanyContactPersonName());

        if (!ObjectUtils.isEmpty(dto.getCompanyContactPersonTitle())) {
            company.setCompanyContactPersonTitle(dto.getCompanyContactPersonTitle());
        }

        if (ObjectUtils.isEmpty(dto.getCompanyContactPersonEmail())) {
            company.setCompanyContactPersonEmail(dto.getEmail());
        } else {
            company.setCompanyContactPersonEmail(dto.getCompanyContactPersonEmail());
        }

        if (!ObjectUtils.isEmpty(dto.getCompanyContactPersonPhoneNumber())) {
            company.setCompanyContactPersonPhoneNumber(dto.getCompanyContactPersonPhoneNumber());
        }

        companyRepo.save(company);
        return acc;
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

        List<Program> programList = new ArrayList<>();
        dto.getProgramList().forEach(programID -> programList.add(programService.get(programID)));
        university.setProgramList(programList);

        if (!ObjectUtils.isEmpty(dto.getAddress())) {
            university.setAddress(dto.getAddress());
        }

        if (!ObjectUtils.isEmpty(dto.getPhoneNumber())) {
            university.setPhoneNumber(dto.getPhoneNumber());
        }

        universityRepo.save(university);
        return acc;
    }

    @Override
    public Account createNewAdmin(AdminAccountDTO dto) {
        Locale locale = LocaleContextHolder.getLocale();

        if (ObjectUtils.isEmpty(dto.getEmail())) {
            throw new InvalidException(messageSource.getMessage("error.account.email-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getPassword())) {
            throw new InvalidException(messageSource.getMessage("error.account.password-empty", null, locale));
        }

        Account acc = new Account();
        acc.setEmail(dto.getEmail());
        acc.setPassword(dto.getPassword());
        acc.setRole(EnumRole.ROLE_ADMIN.name());

        accountRepo.save(acc);
        return acc;
    }

    @Override
    public Account changePassword(int id, ChangePasswordDTO changePasswordDTO) {
        Locale locale = LocaleContextHolder.getLocale();

        Account account = get(id);
        String oldPass = changePasswordDTO.getOldPass();
        String newPass = changePasswordDTO.getNewPass();

        if (ObjectUtils.isEmpty(oldPass)) {
            throw new InvalidException(messageSource.getMessage("error.account.oldpassword-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(newPass)) {
            throw new InvalidException(messageSource.getMessage("error.account.newpassword-empty", null, locale));
        }

        if (!oldPass.equals(account.getPassword())) {
            throw new InvalidException(messageSource.getMessage("error.account.oldpass-notmatch", null, locale));
        }

        account.setPassword(newPass);
        accountRepo.save(account);

        return account;
    }

    @Override
    public Account changeStatus(int id) {
        Account account = get(id);

        account.setStatus(!account.getStatus());
        return account;
    }
}
