package com.gsquad.lunih.services.company;

import com.gsquad.lunih.dtos.accountDTO.CompanyAccountDTO;
import com.gsquad.lunih.dtos.company.ApproveCompanyDTO;
import com.gsquad.lunih.entities.Company;
import com.gsquad.lunih.entities.categories.Industry;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.CompanyRepo;
import com.gsquad.lunih.services.industry.IndustryService;
import com.gsquad.lunih.utils.PageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo companyRepo;
    private final IndustryService industryService;
    private final MessageSource messageSource;

    public CompanyServiceImpl(CompanyRepo companyRepo, IndustryService industryService, MessageSource messageSource) {
        this.companyRepo = companyRepo;
        this.industryService = industryService;
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
        Locale locale = LocaleContextHolder.getLocale();
        return companyRepo.findByAccount_Email(principal.getName())
                .orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.company.email-notfound", null, locale), principal.getName())));
    }

    @Override
    public Company update(int companyID, CompanyAccountDTO dto) {
        Locale locale = LocaleContextHolder.getLocale();

        Company company = get(companyID);

        if (ObjectUtils.isEmpty(dto.getCompanyName())) {
            throw new InvalidException(messageSource.getMessage("error.company.name-empty", null, locale));
        }

        company.setCompanyName(dto.getCompanyName());
        company.setCompanyDescription(dto.getCompanyDescription());
        company.setCompanyType(dto.getCompanyType());

        List<Industry> industryList = new ArrayList<>();
        dto.getIndustryList().forEach(industryID -> industryList.add(industryService.get(industryID)));
        company.setIndustryList(industryList);

        company.setCompanyAddress(dto.getCompanyAddress());
        company.setCompanyWebsite(dto.getCompanyWebsite());
        company.setCompanyLogo(dto.getCompanyLogo());

        if (ObjectUtils.isEmpty(dto.getCompanyContactPersonName())) {
            throw new InvalidException(messageSource.getMessage("error.company.personName-empty", null, locale));
        }

        company.setCompanyContactPersonName(dto.getCompanyContactPersonName());
        company.setCompanyContactPersonTitle(dto.getCompanyContactPersonTitle());
        company.setCompanyContactPersonEmail(dto.getCompanyContactPersonEmail());
        company.setCompanyContactPersonPhoneNumber(dto.getCompanyContactPersonPhoneNumber());

        companyRepo.save(company);
        return company;
    }

//    @Override
//    public Company approveCompany(int companyID, ApproveCompanyDTO approveCompanyDTO) {
//        Locale locale = LocaleContextHolder.getLocale();
//
//        Company company = get(companyID);
//
//
//        company.setApproved(approveCompanyDTO.getIsApproved());
//        if (!approveCompanyDTO.getIsApproved()) {
//
//            if (ObjectUtils.isEmpty(approveCompanyDTO.getReason())) {
//                throw new InvalidException(messageSource.getMessage("error.approve.reason-empty", null, locale));
//            }
//            company.setReason(approveCompanyDTO.getReason());
//            // TODO: send mail
//        }
//
//        companyRepo.save(company);
//        return company;
//    }

    @Override
    public Company delete(int companyID) {
        Company company = get(companyID);

        companyRepo.delete(company);
        return company;
    }
}
