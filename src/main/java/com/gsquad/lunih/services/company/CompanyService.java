package com.gsquad.lunih.services.company;

import com.gsquad.lunih.dtos.accountDTO.CompanyAccountDTO;
import com.gsquad.lunih.dtos.company.ApproveCompanyDTO;
import com.gsquad.lunih.entities.Company;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface CompanyService {
    Page<Company> listAllPaging(String search, int page, int size, String sort, String column);

    List<Company> listAll();

    Company get(int companyID);

    Company getCurrent(Principal principal);

    Company update(int companyID, CompanyAccountDTO dto);

    Company approveCompany(int companyID, ApproveCompanyDTO approveCompanyDTO);


    Company delete(int companyID);
}
