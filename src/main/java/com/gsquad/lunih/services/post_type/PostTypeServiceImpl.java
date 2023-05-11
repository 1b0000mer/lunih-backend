package com.gsquad.lunih.services.post_type;

import com.gsquad.lunih.dtos.categories.PostTypeDTO;
import com.gsquad.lunih.entities.categories.PostType;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.categories.PostTypeRepo;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class PostTypeServiceImpl implements PostTypeService {

    private final PostTypeRepo postTypeRepo;

    private final MessageSource messageSource;

    public PostTypeServiceImpl(PostTypeRepo postTypeRepo, MessageSource messageSource) {
        this.postTypeRepo = postTypeRepo;
        this.messageSource = messageSource;
    }

    /*@Override
    public Page<PostType> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

       return postTypeRepo.getAllPostTypePaging(search, pageable);
    }*/

    @Override
    public List<PostType> listAll() {
        return postTypeRepo.findAll();
    }

    @Override
    public PostType get(int id) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        return postTypeRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.postType.ID-notfound", null , locale),id)));
    }

    @Override
    public PostType create(PostTypeDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.postType.nameEn-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.postType.nameLv-empty", null, locale));
        }

        // TODO: handle logic
        PostType postType = new PostType();
        postType.setNameEn(dto.getNameEn());
        postType.setNameLv(dto.getNameLv());
        postType.setStatus(true);

        postTypeRepo.save(postType);
        return postType;
    }

    @Override
    public PostType update(int id, PostTypeDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        PostType postType = get(id);

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.postType.nameEn-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.postType.nameLv-empty", null, locale));
        }

        // TODO: handle logic
        postType.setNameEn(dto.getNameEn());
        postType.setNameLv(dto.getNameLv());

        postTypeRepo.save(postType);

        return postType;
    }

    @Override
    public PostType changeStatus(int id) {
       PostType postType = get(id);

        postType.setStatus(!postType.getStatus());

        postTypeRepo.save(postType);
        return postType;
    }

    @Override
    public PostType delete(int id) {
        PostType postType = get(id);

        postTypeRepo.delete(postType);
        return postType;
    }
}
