package com.gsquad.lunih.services.account;

import com.gsquad.lunih.dtos.accountDTO.CompanyAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.UniversityAccountDTO;
import com.gsquad.lunih.entities.Account;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.repos.AccountRepo;
import com.gsquad.lunih.repos.StudentRepo;
import com.gsquad.lunih.utils.EnumRole;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final StudentRepo studentRepo;

    public AccountServiceImpl(AccountRepo accountRepo, StudentRepo studentRepo) {
        this.accountRepo = accountRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public List<Account> listAll() {
        return accountRepo.findAll();
    }

    @Override
    public Account get(int id) {
        return accountRepo.findById(id).get();
    }

    @Override
    public Account createNewStudent(StudentAccountDTO dto) {
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
        student.setBirthDay(dto.getBirthDay());
        student.setGender(dto.getGender());
        student.setPhoneNumber(dto.getPhoneNumber());
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
        return null;
    }
}
