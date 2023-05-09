package com.gsquad.lunih.services.spectrum;

import com.gsquad.lunih.dtos.categories.SpectrumDTO;
import com.gsquad.lunih.entities.categories.Spectrum;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.categories.SpectrumRepo;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class SpectrumServiceImpl implements SpectrumService {

    private final SpectrumRepo spectrumRepo;

    private final MessageSource messageSource;

    public SpectrumServiceImpl(SpectrumRepo spectrumRepo, MessageSource messageSource) {
        this.spectrumRepo = spectrumRepo;
        this.messageSource = messageSource;
    }

    /*@Override
    public Page<PostType> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

       return postTypeRepo.getAllPostTypePaging(search, pageable);
    }*/

    @Override
    public List<Spectrum> listAll() {
        return spectrumRepo.findAll();
    }

    @Override
    public Spectrum get(int id) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        return spectrumRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.spectrum.ID-notfound", null , locale),id)));
    }

    @Override
    public Spectrum create(SpectrumDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException(messageSource.getMessage("error.spectrum.name-empty", null, locale));
        }


        // TODO: handle logic
        Spectrum spectrum = new Spectrum();
        spectrum.setName(dto.getName());
        spectrum.setStatus(true);

        spectrumRepo.save(spectrum);
        return spectrum;
    }

    @Override
    public Spectrum update(int id, SpectrumDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException(messageSource.getMessage("error.spectrum.name-empty", null, locale));
        }


        // TODO: handle logic
        Spectrum spectrum = get(id);
        spectrum.setName(dto.getName());
        spectrum.setStatus(true);

        spectrumRepo.save(spectrum);
        return spectrum;
    }

    @Override
    public Spectrum changeStatus(int id) {
        Spectrum spectrum = get(id);

        spectrum.setStatus(!spectrum.getStatus());

        spectrumRepo.save(spectrum);
        return spectrum;
    }

    @Override
    public Spectrum delete(int id) {
        Spectrum spectrum = get(id);

        spectrumRepo.delete(spectrum);
        return spectrum;
    }
}
