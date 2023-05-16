package com.gsquad.lunih.services.deliverable;

import com.gsquad.lunih.dtos.DeliverableDTO;
import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.dtos.categories.FacultyDTO;
import com.gsquad.lunih.entities.Deliverable;
import com.gsquad.lunih.entities.Post;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.entities.categories.Faculty;
import com.gsquad.lunih.entities.categories.Industry;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.DeliverableRepo;
import com.gsquad.lunih.repos.PostRepo;
import com.gsquad.lunih.repos.categories.FacultyRepo;
import com.gsquad.lunih.services.account.AccountService;
import com.gsquad.lunih.services.faculty.FacultyService;
import com.gsquad.lunih.services.industry.IndustryService;
import com.gsquad.lunih.services.post.PostService;
import com.gsquad.lunih.services.post_type.PostTypeService;
import com.gsquad.lunih.services.student.StudentService;
import com.gsquad.lunih.utils.PageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class DeliverableServiceImpl implements DeliverableService {

    private final DeliverableRepo deliverableRepo;

    private final MessageSource messageSource;

    public DeliverableServiceImpl(DeliverableRepo deliverableRepo, MessageSource messageSource) {
        this.deliverableRepo = deliverableRepo;
        this.messageSource = messageSource;
    }

    @Override
    public Page<Deliverable> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

        return deliverableRepo.getAllDeliverablePaging(search, pageable);
    }

    @Override
    public List<Deliverable> listAll() {
        return deliverableRepo.findAll();
    }

    @Override
    public Deliverable get(int id) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        return deliverableRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.deliverable.id-notfound", null , locale),id)));
    }

    @Override
    public Deliverable create(DeliverableDTO dto) {
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
        Deliverable deliverable = new Deliverable();
        deliverable.setNameEn(dto.getNameEn());
        deliverable.setNameLv(dto.getNameLv());
        deliverable.setFileAttachment(dto.getFileAttachment());
        deliverable.setCreatedDate(dto.getCreatedDate());
        deliverable.setCreatedBy(dto.getCreatedBy());


        deliverableRepo.save(deliverable);
        return deliverable;
    }

    @Override
    public Deliverable update(int id, DeliverableDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();

        Deliverable deliverable = get(id);

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.deliverable.nameEn-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.deliverable.nameLv-empty", null, locale));
        }

        // TODO: handle logic
        deliverable.setNameEn(dto.getNameEn());
        deliverable.setNameLv(dto.getNameLv());
        deliverable.setFileAttachment(dto.getFileAttachment());

        deliverableRepo.save(deliverable);
        return deliverable;
    }

   /* @Override
    public Deliverable changeStatus(int id) {
        Deliverable deliverable = get(id);

        deliverable.setStatus(!deliverable.getStatus());

        deliverableRepo.save(deliverable);
        return deliverable;
    }*/

    @Override
    public Deliverable delete(int id) {
        Deliverable deliverable = get(id);

        deliverableRepo.delete(deliverable);
        return deliverable;
    }
}
