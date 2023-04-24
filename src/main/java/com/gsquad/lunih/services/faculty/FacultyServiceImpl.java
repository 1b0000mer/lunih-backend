package com.gsquad.lunih.services.faculty;

import com.gsquad.lunih.dtos.categories.FacultyDTO;
import com.gsquad.lunih.entities.categories.Faculty;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.categories.FacultyRepo;
import com.gsquad.lunih.utils.PageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepo facultyRepo;

    private final MessageSource messageSource;

    public FacultyServiceImpl(FacultyRepo facultyRepo, MessageSource messageSource) {
        this.facultyRepo = facultyRepo;
        this.messageSource = messageSource;
    }

    @Override
    public Page<Faculty> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

        return facultyRepo.getAllFacultyPaging(search, pageable);
    }

    @Override
    public List<Faculty> listAll() {
        return facultyRepo.findAll();
    }

    @Override
    public Faculty get(int id) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

//        return facultyRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.faculty.id-notfound", null , locale),id)));
        if (facultyRepo.findById(id).isPresent()) {
            return facultyRepo.findById(id).get();
        } else {
            throw new NotFoundException(String.format(messageSource.getMessage("error.faculty.id-notfound", null , locale),id));
        }
    }

    @Override
    public Faculty create(FacultyDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.faculty.nameEn-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.faculty.nameLv-empty", null, locale));
        }

        // TODO: handle logic
        Faculty faculty = new Faculty();
        faculty.setNameEn(dto.getNameEn());
        faculty.setNameLv(dto.getNameLv());
        faculty.setStatus(true);

        facultyRepo.save(faculty);
        return faculty;
    }

    @Override
    public Faculty update(int id, FacultyDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        Faculty faculty = get(id);

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.faculty.nameEn-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.faculty.nameLv-empty", null, locale));
        }

        // TODO: handle logic
        faculty.setNameEn(dto.getNameEn());
        faculty.setNameLv(dto.getNameLv());

        facultyRepo.save(faculty);
        return faculty;
    }

    @Override
    public Faculty changeStatus(int id) {
        Faculty faculty = get(id);

        faculty.setStatus(!faculty.getStatus());

        facultyRepo.save(faculty);
        return faculty;
    }

    @Override
    public Faculty delete(int id) {
        Faculty faculty = get(id);

        facultyRepo.delete(faculty);
        return faculty;
    }
}
