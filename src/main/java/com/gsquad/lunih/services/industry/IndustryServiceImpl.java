package com.gsquad.lunih.services.industry;

import com.gsquad.lunih.dtos.categories.IndustryDTO;
import com.gsquad.lunih.entities.categories.Industry;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.categories.IndustryRepo;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepo industryRepo;

    private final MessageSource messageSource;

    public IndustryServiceImpl(IndustryRepo industryRepo, MessageSource messageSource) {
        this.industryRepo = industryRepo;
        this.messageSource = messageSource;
    }

    /*@Override
    public Page<PostType> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

       return postTypeRepo.getAllPostTypePaging(search, pageable);
    }*/

    @Override
    public List<Industry> listAll() {
        return industryRepo.findAll();
    }

    @Override
    public Industry get(int id) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        return industryRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.industry.ID-notfound", null , locale),id)));
    }

    @Override
    public Industry create(IndustryDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.industry.nameEn-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.industry.nameLv-empty", null, locale));
        }


        // TODO: handle logic
        Industry industry = new Industry();
        industry.setNameEn(dto.getNameEn());
        industry.setNameLv(dto.getNameLv());
        industry.setStatus(true);

        industryRepo.save(industry);
        return industry;
    }

    @Override
    public Industry update(int id, IndustryDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        Industry industry = get(id);

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.industry.nameEn-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.industry.nameLv-empty", null, locale));
        }


        // TODO: handle logic
        industry.setNameEn(dto.getNameEn());
        industry.setNameLv(dto.getNameLv());

        industryRepo.save(industry);
        return industry;
    }

    @Override
    public Industry changeStatus(int id) {
        Industry industry = get(id);

        industry.setStatus(!industry.getStatus());

        industryRepo.save(industry);
        return industry;
    }

    @Override
    public Industry delete(int id) {
        Industry industry = get(id);

        industryRepo.delete(industry);
        return industry;
    }
}
