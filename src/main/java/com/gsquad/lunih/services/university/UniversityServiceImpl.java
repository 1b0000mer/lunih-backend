package com.gsquad.lunih.services.university;

import com.gsquad.lunih.dtos.accountDTO.UniversityAccountDTO;
import com.gsquad.lunih.entities.University;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.UniversityRepo;
import com.gsquad.lunih.utils.PageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepo universityRepo;

    private final MessageSource messageSource;

    public UniversityServiceImpl(UniversityRepo universityRepo, MessageSource messageSource) {
        this.universityRepo = universityRepo;
        this.messageSource = messageSource;
    }

    @Override
    public Page<University> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

        return universityRepo.getAllUniversityPaging(search, pageable);
    }

    @Override
    public List<University> listAll() {
        return universityRepo.findAll();
    }

    @Override
    public University get(int id) {
        Locale locale = LocaleContextHolder.getLocale();

        return universityRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.university.id-notfound", null, locale), id)));
    }

    @Override
    public University getCurrent(Principal principal) {
        return null;
    }

    @Override
    public University update(int id, UniversityAccountDTO dto) {
        Locale locale = LocaleContextHolder.getLocale();

        University university = get(id);

//        if (ObjectUtils.isEmpty(dto.getName())) {
//            throw new InvalidException(messageSource.getMessage("error.university.name-empty", null, locale));
//        }
//
//        if (ObjectUtils.isEmpty(dto.getAddress())) {
//            throw new InvalidException(messageSource.getMessage("error.university.address-empty", null, locale));
//        }
//
//        if (ObjectUtils.isEmpty(dto.getPhoneNumber())) {
//            throw new InvalidException(messageSource.getMessage("error.university.phoneNumber-empty", null, locale));
//        }

        //nullable
        university.setName(dto.getName());
        university.setAddress(dto.getAddress());
        university.setPhoneNumber(dto.getPhoneNumber());

//        //if change email
//        if (!dto.getEmail().equals(university.getAccount().getEmail())) {
//            university.getAccount().setEmail(dto.getEmail());
//        }
//
//        //if change password
//        if (!dto.getPassword().equals(university.getAccount().getPassword())) {
//            university.getAccount().setPassword(dto.getPassword());
//        }

        universityRepo.save(university);
        return university;
    }

    @Override
    public University delete(int id) {
        University university = get(id);

        universityRepo.delete(university);
        return university;
    }
}
