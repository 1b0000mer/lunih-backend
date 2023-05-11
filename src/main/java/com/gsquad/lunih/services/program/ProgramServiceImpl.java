package com.gsquad.lunih.services.program;

import com.gsquad.lunih.dtos.categories.ProgramDTO;
import com.gsquad.lunih.entities.categories.Program;
import com.gsquad.lunih.entities.categories.Industry;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.categories.ProgramRepo;
import com.gsquad.lunih.services.faculty.FacultyService;
import com.gsquad.lunih.services.industry.IndustryService;
import com.gsquad.lunih.utils.PageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepo programRepo;
    private final MessageSource messageSource;
    private final FacultyService facultyService;
    private final IndustryService industryService;

    public ProgramServiceImpl(ProgramRepo programRepo, MessageSource messageSource, FacultyService facultyService, IndustryService industryService) {
        this.programRepo = programRepo;
        this.messageSource = messageSource;
        this.facultyService = facultyService;
        this.industryService = industryService;
    }

    @Override
    public Page<Program> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

        return programRepo.getALLProgramPaging(search, pageable);
    }

    @Override
    public List<Program> listAll() {
        return programRepo.findAll();
    }

    @Override
    public Program get(int id) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        return programRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.program.id-notfound", null , locale),id)));
    }

    @Override
    public Program create(ProgramDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.program.nameEn-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.program.nameLv-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getStudyLevel())) {
            throw new InvalidException(messageSource.getMessage("error.program.studylevel-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getFacultyID())) {
            throw new InvalidException(messageSource.getMessage("error.program.faculty-empty", null, locale));
        }


        // TODO: handle logic
        Program program = new Program();
        program.setNameEn(dto.getNameEn());
        program.setNameLv(dto.getNameLv());
        program.setStudyLevel(dto.getStudyLevel());
        program.setFaculty(facultyService.get(dto.getFacultyID()));

        List<Industry> industryList = new ArrayList<>();
        dto.getSpectrumList().forEach(spectrumID -> industryList.add(industryService.get(spectrumID)));
        program.setIndustryList(industryList);
        program.setStatus(true);

        programRepo.save(program);
        return program;
    }

    @Override
    public Program update(int id, ProgramDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        Program program = get(id);

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.program.nameEn-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.program.nameLv-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getStudyLevel())) {
            throw new InvalidException(messageSource.getMessage("error.program.studylevel-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getFacultyID())) {
            throw new InvalidException(messageSource.getMessage("error.program.faculty-empty", null, locale));
        }

        // TODO: handle logic
        program.setNameEn(dto.getNameEn());
        program.setNameLv(dto.getNameLv());
        program.setStudyLevel(dto.getStudyLevel());
        program.setFaculty(facultyService.get(dto.getFacultyID()));
        List<Industry> industryList = new ArrayList<>();
        dto.getSpectrumList().forEach(spectrumID -> industryList.add(industryService.get(spectrumID)));
        program.setIndustryList(industryList);

        programRepo.save(program);
        return program;
    }

    @Override
    public Program changeStatus(int id) {
        Program program = get(id);

        program.setStatus(!program.getStatus());

        programRepo.save(program);
        return program;
    }

    @Override
    public String delete(int id) {
        Program program = get(id);

        programRepo.delete(program);
        return "Done!";
    }
}
