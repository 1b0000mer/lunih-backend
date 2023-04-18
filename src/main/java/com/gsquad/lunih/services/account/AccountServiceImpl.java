package com.gsquad.lunih.services.account;

import com.gsquad.lunih.dtos.CompanyAccountDTO;
import com.gsquad.lunih.dtos.StudentAccountDTO;
import com.gsquad.lunih.dtos.UniversityAccountDTO;
import com.gsquad.lunih.entities.Account;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.repos.AccountRepo;
import com.gsquad.lunih.repos.StudentRepo;
import com.gsquad.lunih.services.role.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final StudentRepo studentRepo;
    private final RoleService roleService;

    public AccountServiceImpl(AccountRepo accountRepo, StudentRepo studentRepo, RoleService roleService) {
        this.accountRepo = accountRepo;
        this.studentRepo = studentRepo;
        this.roleService = roleService;
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
        acc.setRole(roleService.get(dto.getRole_id()));
        accountRepo.save(acc);

        Student student = new Student();
        student.setStudentID(dto.getStudentID());
        student.setFirstName(dto.getFirstName());
        student.setSurName(dto.getSurName());
        student.setAccount(acc);
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

    @Override
    public Account delete(int id) {
        Account acc = get(id);
        accountRepo.delete(acc);
        return acc;
    }
}
