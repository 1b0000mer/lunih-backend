package com.gsquad.lunih.services.company;

import com.gsquad.lunih.dtos.accountDTO.CompanyAccountDTO;
import com.gsquad.lunih.dtos.company.ApproveCompanyDTO;
import com.gsquad.lunih.entities.Company;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.CompanyRepo;
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
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo companyRepo;
    private final MessageSource messageSource;

    public CompanyServiceImpl(CompanyRepo companyRepo, MessageSource messageSource) {
        this.companyRepo = companyRepo;
        this.messageSource = messageSource;
    }

    @Override
    public Page<Company> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

        return companyRepo.getAllCompanyPaging(search, pageable);
    }

    @Override
    public List<Company> listAll() {
        return companyRepo.findAll();
    }

    @Override
    public Company get(int companyID) {
        Locale locale = LocaleContextHolder.getLocale();

        return companyRepo.findById(companyID).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.company.companyID-notfound", null, locale), companyID)));
    }

    @Override
    public Company getCurrent(Principal principal) {
        return null;
    }

    @Override
    public Company update(int companyID, CompanyAccountDTO dto) {
        Locale locale = LocaleContextHolder.getLocale();

        Company company = get(companyID);

//        if (ObjectUtils.isEmpty(dto.getStudentID())) {
//            throw new InvalidException(messageSource.getMessage("error.student.studentID-empty", null, locale));
//        }

        if (ObjectUtils.isEmpty(dto.getCompanyName())) {
            throw new InvalidException(messageSource.getMessage("error.company.companyName-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getCompanyPersonName())) {
            throw new InvalidException(messageSource.getMessage("error.company.companyPersonName-empty", null, locale));
        }

//        //check exist
//        if (studentRepo.existsById(dto.getStudentID())) {
//            throw new InvalidException(String.format(messageSource.getMessage("error.student.studentID-exist", null, locale), dto.getStudentID()));
//        }

//        company.setStudentID(dto.getStudentID());
        company.setCompanyName(dto.getCompanyName());
        company.setCompanyPersonName(dto.getCompanyPersonName());

        /*if (!ObjectUtils.isEmpty(dto.getBirthDay())) {
            student.setBirthDay(dto.getBirthDay());
        }

        if (!ObjectUtils.isEmpty(dto.getGender())) {
            student.setGender(dto.getGender());
        }*/

        if (!ObjectUtils.isEmpty(dto.getPhoneNumber())) {
            company.setPhoneNumber(dto.getPhoneNumber());
        }
        if (!ObjectUtils.isEmpty(dto.getAddress())) {
            company.setAddress(dto.getAddress());
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

        companyRepo.save(company);
        return company;
    }

 @Override
    public Company approveCompany(int companyID, ApproveCompanyDTO approveCompanyDTO) {
        Locale locale = LocaleContextHolder.getLocale();

        Company company = get(companyID);


     company.setApproved(approveCompanyDTO.getIsApproved());
        if (!approveCompanyDTO.getIsApproved()) {

            if (ObjectUtils.isEmpty(approveCompanyDTO.getReason())) {
                throw new InvalidException(messageSource.getMessage("error.approve.reason-empty", null, locale));
            }
            company.setReason(approveCompanyDTO.getReason());
            // TODO: send mail
        }

        companyRepo.save(company);
        return company;
    }

    @Override
    public Company delete(int companyID) {
        Company company = get(companyID);

        companyRepo.delete(company);
        return company;
    }
}
