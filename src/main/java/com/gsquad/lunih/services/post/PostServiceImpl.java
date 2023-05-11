package com.gsquad.lunih.services.post;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.entities.Post;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.entities.categories.Industry;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.PostRepo;
import com.gsquad.lunih.services.industry.IndustryService;
import com.gsquad.lunih.services.post_type.PostTypeService;
import com.gsquad.lunih.services.student.StudentService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    private final StudentService studentService;

    private final MessageSource messageSource;
    private final IndustryService industryService;

    private final PostTypeService postTypeService;

    public PostServiceImpl(PostRepo postRepo, StudentService studentService, MessageSource messageSource, IndustryService industryService, PostTypeService postTypeService) {
        this.postRepo = postRepo;
        this.studentService = studentService;
        this.messageSource = messageSource;
        this.industryService = industryService;
        this.postTypeService = postTypeService;
    }

    @Override
    public Page<Post> listAllPaging(String search, int page, int size, String sort, String column) {
        return null;
    }

    @Override
    public List<Post> listAll() {
        return null;
    }

    @Override
    public Post get(int id) {
        Locale locale = LocaleContextHolder.getLocale();
         return postRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.post.id-notfound", null , locale),id)));

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

        Post post = new Post();

        post.setTitleEn(dto.getTitleEn());
        post.setTitleLv(dto.getTitleLv());
        post.setDescriptionEn(dto.getDescriptionEn());
        post.setDescriptionLv(dto.getDescriptionLv());
        post.setPostType(postTypeService.get(dto.getPostType()));
        post.setStartDate(dto.getStartDate());
        post.setEndDate(dto.getEndDate());
        post.setNumSlot(dto.getNumSlot());



        List<Industry> industryList = new ArrayList<>();
        dto.getIndustryList().forEach(industryID -> industryList.add(industryService.get(industryID)));
        post.setIndustryList(industryList);

        List<Student> studentList = new ArrayList<>();
        dto.getStudentList().forEach(studentID -> studentList.add(studentService.get(studentID)));
        post.setStudentList(studentList);

        List<Student> queueList = new ArrayList<>();
        dto.getQueueList().forEach(studentID -> queueList.add(studentService.get(String.valueOf(studentID))));
        post.setQueueList(queueList);

        /*List<Deliverable> deliverables = new ArrayList<>();
        dto.getDeliverables().forEach(deliveabreID -> deliverables.add(deliveabreService.get(deliveabreID.getId())));
        post.setDeliverables(deliverables);*/

        // TODO:
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

        post.setTitleEn(dto.getTitleEn());
        post.setTitleLv(dto.getTitleLv());
        post.setDescriptionEn(dto.getDescriptionEn());
        post.setDescriptionLv(dto.getDescriptionLv());
        post.setPostType(postTypeService.get(dto.getPostType()));
        post.setStartDate(dto.getStartDate());
        post.setEndDate(dto.getEndDate());
        post.setNumSlot(dto.getNumSlot());

        List<Industry> industryList = new ArrayList<>();
        dto.getIndustryList().forEach(industryID -> industryList.add(industryService.get(industryID)));
        post.setIndustryList(industryList);

        List<Student> studentList = new ArrayList<>();
        dto.getStudentList().forEach(studentID -> studentList.add(studentService.get(studentID)));
        post.setStudentList(studentList);

        List<Student> queueList = new ArrayList<>();
        dto.getQueueList().forEach(studentID -> queueList.add(studentService.get(String.valueOf(studentID))));
        post.setQueueList(queueList);

        /*List<Deliverable> deliverables = new ArrayList<>();
        dto.getDeliverables().forEach(deliveabreID -> deliverables.add(deliveabreService.get(deliveabreID.getId())));
        post.setDeliverables(deliverables);*/

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
}
