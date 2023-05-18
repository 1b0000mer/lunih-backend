package com.gsquad.lunih.services.deliverable;

import com.gsquad.lunih.dtos.deliverables.ChangeStatusDTO;
import com.gsquad.lunih.dtos.deliverables.DeliverableDTO;
import com.gsquad.lunih.entities.Deliverable;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.DeliverableRepo;
import com.gsquad.lunih.utils.PageUtils;
import com.gsquad.lunih.utils.TimeUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
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
        Date toDay = new Date();

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.deliverable.nameEn-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.deliverable.nameLv-empty", null, locale));
        }

        // TODO: handle logic
        Deliverable deliverable = new Deliverable();
        deliverable.setNameEn(dto.getNameEn());
        deliverable.setNameLv(dto.getNameLv());
        deliverable.setFileAttachment(dto.getFileAttachment());
        deliverable.setStatus(dto.getStatus());

        if (!ObjectUtils.isEmpty(dto.getDeadLine())) {
            if (TimeUtils.checkTime(toDay, dto.getDeadLine())) {
                throw new InvalidException(messageSource.getMessage("error.deliverable.time-invalid", null, locale));
            } else {
                deliverable.setDeadLine(dto.getDeadLine());
            }
        }

        deliverableRepo.save(deliverable);
        return deliverable;
    }

    @Override
    public Deliverable update(int id, DeliverableDTO dto) {
        //get locale code (en, lv)
        Locale locale = LocaleContextHolder.getLocale();
        Date toDay = new Date();

        Deliverable deliverable = get(id);

        // TODO: validate input before handle
        if (ObjectUtils.isEmpty(dto.getNameEn())) {
            throw new InvalidException(messageSource.getMessage("error.deliverable.nameEn-empty", null, locale));
        }

        if (ObjectUtils.isEmpty(dto.getNameLv())) {
            throw new InvalidException(messageSource.getMessage("error.deliverable.nameLv-empty", null, locale));
        }

        if (!ObjectUtils.isEmpty(dto.getDeadLine())) {
            if (TimeUtils.checkTime(toDay, dto.getDeadLine())) {
                throw new InvalidException(messageSource.getMessage("error.deliverable.time-invalid", null, locale));
            } else {
                deliverable.setDeadLine(dto.getDeadLine());
            }
        }

        // TODO: handle logic
        deliverable.setNameEn(dto.getNameEn());
        deliverable.setNameLv(dto.getNameLv());
        deliverable.setFileAttachment(dto.getFileAttachment());
        deliverable.setStatus(dto.getStatus());

        deliverableRepo.save(deliverable);
        return deliverable;
    }

    @Override
    public Deliverable changeStatus(int id, ChangeStatusDTO dto) {
        Deliverable deliverable = get(id);

        deliverable.setStatus(dto.getStatus());

        deliverableRepo.save(deliverable);
        return deliverable;
    }

    @Override
    public Deliverable delete(int id) {
        Deliverable deliverable = get(id);

        deliverableRepo.delete(deliverable);
        return deliverable;
    }
}
