package com.gsquad.lunih.services.account;

import com.gsquad.lunih.dtos.accountDTO.CompanyAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.UniversityAccountDTO;
import com.gsquad.lunih.entities.Account;

import java.util.List;

public interface AccountService {

    List<Account> listAll();

    Account get(int id);

    Account createNewStudent(StudentAccountDTO dto);
    Account createNewCompany(CompanyAccountDTO dto);
    Account createNewUniversity(UniversityAccountDTO dto);

    Account delete(int id);
}
