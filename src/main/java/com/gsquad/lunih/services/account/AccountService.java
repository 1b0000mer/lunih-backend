package com.gsquad.lunih.services.account;

import com.gsquad.lunih.dtos.accountDTO.ChangePasswordDTO;
import com.gsquad.lunih.dtos.accountDTO.CompanyAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.UniversityAccountDTO;
import com.gsquad.lunih.entities.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    Page<Account> listAllPaging(String search, int page, int size, String sort, String column);

    List<Account> listAll();

    Account get(int id);

    Account createNewStudent(StudentAccountDTO dto);
    Account createNewCompany(CompanyAccountDTO dto);
    Account createNewUniversity(UniversityAccountDTO dto);

    Account changePassword(int id, ChangePasswordDTO changePasswordDTO);

}
