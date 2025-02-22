package com.gsquad.lunih.services.post;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.dtos.deliverables.DeliverableDTO;
import com.gsquad.lunih.entities.*;
import com.gsquad.lunih.entities.categories.Industry;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.PostRepo;
import com.gsquad.lunih.services.account.AccountService;
import com.gsquad.lunih.services.company.CompanyService;
import com.gsquad.lunih.services.deliverable.DeliverableService;
import com.gsquad.lunih.services.industry.IndustryService;
import com.gsquad.lunih.services.post_type.PostTypeService;
import com.gsquad.lunih.services.student.StudentService;
import com.gsquad.lunih.services.university.UniversityService;
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

import static com.gsquad.lunih.utils.TimeUtils.checkTime;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final StudentService studentService;
    private final MessageSource messageSource;
    private final IndustryService industryService;
    private final PostTypeService postTypeService;
    private final AccountService accountService;
    private final DeliverableService deliverableService;
    private final UniversityService universityService;
    private final CompanyService companyService;

    public PostServiceImpl(PostRepo postRepo, StudentService studentService, MessageSource messageSource, IndustryService industryService, PostTypeService postTypeService, AccountService accountService, DeliverableService deliverableService, UniversityService universityService, CompanyService companyService) {
        this.postRepo = postRepo;
        this.studentService = studentService;
        this.messageSource = messageSource;
        this.industryService = industryService;
        this.postTypeService = postTypeService;
        this.accountService = accountService;
        this.deliverableService = deliverableService;
        this.universityService = universityService;
        this.companyService = companyService;
    }

    @Override
    public Page<Post> listAllPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);

        return postRepo.getAllPostPaging(search, pageable);
    }

    @Override
    public List<Post> listAll() {
        return postRepo.findAll();
    }

    @Override
    public Post get(int id) {
        Locale locale = LocaleContextHolder.getLocale();
        return postRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.post.id-notfound", null, locale), id)));

    }

    @Override
    public Post create(PostDTO dto) {
        Locale locale = LocaleContextHolder.getLocale();

        if (ObjectUtils.isEmpty(dto.getTitleEn())) {
            throw new InvalidException(messageSource.getMessage("error.post.titleEn-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getTitleLv())) {
            throw new InvalidException(messageSource.getMessage("error.post.titleLv-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getDescriptionEn())) {
            throw new InvalidException(messageSource.getMessage("error.post.descriptionEn-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getDescriptionLv())) {
            throw new InvalidException(messageSource.getMessage("error.post.descriptionLv-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getStartDate())) {
            throw new InvalidException(messageSource.getMessage("error.post.startdate-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getEndDate())) {
            throw new InvalidException(messageSource.getMessage("error.post.enddate-empty", null, locale));
        }
        if (checkTime(dto.getStartDate(), dto.getEndDate())) {
            throw new InvalidException(messageSource.getMessage("error.post.time-invalid", null, locale));
        }
        if (dto.getNumSlot() <= 0) {
            throw new InvalidException(messageSource.getMessage("error.post.slot-invalid", null, locale));
        }

        Post post = new Post();

        post.setTitleEn(dto.getTitleEn());
        post.setTitleLv(dto.getTitleLv());
        post.setDescriptionEn(dto.getDescriptionEn());
        post.setDescriptionLv(dto.getDescriptionLv());
        post.setPostType(postTypeService.get(dto.getPostType()));
        post.setStartDate(dto.getStartDate());
        post.setEndDate(dto.getEndDate());
        post.setNumSlot(dto.getNumSlot());
        if (dto.getAuthor() != -1) {
            post.setAuthor(accountService.get(dto.getAuthor()));
        }

        List<Industry> industryList = new ArrayList<>();
        dto.getIndustryList().forEach(industryID -> industryList.add(industryService.get(industryID)));
        post.setIndustryList(industryList);

        List<Student> studentList = new ArrayList<>();
        dto.getStudentList().forEach(studentID -> studentList.add(studentService.get(studentID)));
        post.setStudentList(studentList);

        List<Student> queueList = new ArrayList<>();
        dto.getQueueList().forEach(studentID -> queueList.add(studentService.get(String.valueOf(studentID))));
        post.setQueueList(queueList);

        List<Deliverable> deliverables = new ArrayList<>();
        dto.getDeliverables().forEach(deliverableID -> deliverables.add(deliverableService.get(deliverableID)));
        post.setDeliverables(deliverables);

        // TODO: status of the post, true for now
        post.setStatus(true);

        postRepo.save(post);
        return post;
    }

    @Override
    public Post update(int postID, PostDTO dto) {
        Locale locale = LocaleContextHolder.getLocale();

        Post post = get(postID);

        if (ObjectUtils.isEmpty(dto.getTitleEn())) {
            throw new InvalidException(messageSource.getMessage("error.post.titleEn-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getTitleLv())) {
            throw new InvalidException(messageSource.getMessage("error.post.titleLv-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getDescriptionEn())) {
            throw new InvalidException(messageSource.getMessage("error.post.descriptionEn-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getDescriptionLv())) {
            throw new InvalidException(messageSource.getMessage("error.post.descriptionLv-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getStartDate())) {
            throw new InvalidException(messageSource.getMessage("error.post.startdate-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getEndDate())) {
            throw new InvalidException(messageSource.getMessage("error.post.enddate-empty", null, locale));
        }
        if (checkTime(dto.getStartDate(), dto.getEndDate())) {
            throw new InvalidException(messageSource.getMessage("error.post.time-invalid", null, locale));
        }
        if (dto.getNumSlot() <= 0) {
            throw new InvalidException(messageSource.getMessage("error.post.slot-invalid", null, locale));
        }

        post.setTitleEn(dto.getTitleEn());
        post.setTitleLv(dto.getTitleLv());
        post.setDescriptionEn(dto.getDescriptionEn());
        post.setDescriptionLv(dto.getDescriptionLv());
        post.setPostType(postTypeService.get(dto.getPostType()));
        post.setStartDate(dto.getStartDate());
        post.setEndDate(dto.getEndDate());
        post.setNumSlot(dto.getNumSlot());
        if (dto.getAuthor() != -1) {
            post.setAuthor(accountService.get(dto.getAuthor()));
        }

        List<Industry> industryList = new ArrayList<>();
        dto.getIndustryList().forEach(industryID -> industryList.add(industryService.get(industryID)));
        post.setIndustryList(industryList);

        List<Student> studentList = new ArrayList<>();
        dto.getStudentList().forEach(studentID -> studentList.add(studentService.get(studentID)));

        if (studentList.size() > dto.getNumSlot()) {
            throw new InvalidException(messageSource.getMessage("error.post.studentList-exceed", null, locale));
        }


        post.setStudentList(studentList);

        List<Student> queueList = new ArrayList<>();
        dto.getQueueList().forEach(studentID -> {
            if (dto.getStudentList().contains(studentID)) {
                throw new InvalidException(String.format(messageSource.getMessage("error.post.queueList-exist", null, locale), studentID));
            }
            queueList.add(studentService.get(String.valueOf(studentID)));
        });


        post.setQueueList(queueList);

        List<Deliverable> deliverables = new ArrayList<>();
        dto.getDeliverables().forEach(deliverableID -> deliverables.add(deliverableService.get(deliverableID)));
        post.setDeliverables(deliverables);

        postRepo.save(post);
        return post;
    }

    @Override
    public Post changeStatus(int id) {
        Post post = get(id);
        post.setStatus(!post.getStatus());

        postRepo.save(post);
        return post;
    }

    @Override
    public Post delete(int id) {
        Post post = get(id);

        postRepo.delete(post);
        return post;
    }

    @Override
    public Post studentApplyPost(Principal principal, int id) {
        Locale locale = LocaleContextHolder.getLocale();

        Student student = studentService.getCurrent(principal);
        Post post = get(id);

//        //already applied other post
//        if (postRepo.findPostsByStudentID(student.getStudentID()).size() > 0) {
//            throw new InvalidException(String.format(messageSource.getMessage("error.student.post-applied", null, locale), student.getStudentID()));
//        }
//
//        //already applied or on queue list
//        if (post.getStudentList().contains(student) || post.getQueueList().contains(student)) {
//            throw new InvalidException(String.format(messageSource.getMessage("error.post.student-existed", null, locale), student.getStudentID()));
//        }


        if (post.getStudentList().size() < post.getNumSlot()) {
            List<Student> studentList = post.getStudentList();
            studentList.add(student);
            post.setStudentList(studentList);
        } else {
            List<Student> queueList = post.getQueueList();
            queueList.add(student);
            post.setQueueList(queueList);
        }

        postRepo.save(post);
        return post;
    }
    @Override
    public Post universityPublishPost(Principal principal, PostDTO dto){
        University university = universityService.getCurrent(principal);
        dto.setAuthor(university.getAccount().getId());
       return create(dto);
    }
    @Override
    public Post companyPublishPost(Principal principal, PostDTO dto){
        Company company = companyService.getCurrent(principal);
        dto.setAuthor(company.getAccount().getId());
        return create(dto);
    }

//    @Override
//    public Post studentUplodeDeliverable(Principal principal, DeliverableDTO dto){
//        Student student = studentService.getCurrent(principal);
//        return create(dto);
//    }
}
