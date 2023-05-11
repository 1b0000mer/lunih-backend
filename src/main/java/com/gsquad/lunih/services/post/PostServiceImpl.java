package com.gsquad.lunih.services.post;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.entities.Deliverable;
import com.gsquad.lunih.entities.Post;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.entities.categories.Spectrum;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.PostRepo;
import com.gsquad.lunih.services.spectrum.SpectrumService;
import com.gsquad.lunih.services.student.StudentService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final SpectrumService spectrumService;

    public PostServiceImpl(PostRepo postRepo, StudentService studentService, MessageSource messageSource, SpectrumService spectrumService) {
        this.postRepo = postRepo;
        this.studentService = studentService;
        this.messageSource = messageSource;
        this.spectrumService = spectrumService;
    }

    @Override
    public List<Post> listAll() {
        return postRepo.findAll();
    }

    @Override
    public Post get(int id) {
        Locale locale = LocaleContextHolder.getLocale();
         return postRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.post.id-notfound", null , locale),id)));

    }

    @Override
    public Post create(PostDTO dto) {
        Post post = new Post();
        List<Student> studentList = new ArrayList<>();
        dto.getStudentList().forEach(studentID -> studentList.add(studentService.get(studentID)));
        post.setStudentList(studentList);
        Locale locale = LocaleContextHolder.getLocale();
        if (ObjectUtils.isEmpty(dto.getTitle())) {
            throw new InvalidException(messageSource.getMessage("error.post.title-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getDescription())) {
            throw new InvalidException(messageSource.getMessage("error.post.description-empty", null, locale));
        }

        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setPostType(dto.getPostType());
        post.setStartDate(dto.getStartDate());
        post.setEndDate(dto.getEndDate());
        post.setNumSlot(dto.getNumSlot());



        List<Spectrum> spectrumList = new ArrayList<>();
        dto.getSpectrumList().forEach(spectrumID -> spectrumList.add(spectrumService.get(spectrumID.getId())));
        post.setSpectrumList(spectrumList);

        List<Student> queueList = new ArrayList<>();
        dto.getQueueList().forEach(studentID -> queueList.add(studentService.get(String.valueOf(studentID))));
        post.setQueueList(queueList);

        /*List<Deliverable> deliverables = new ArrayList<>();
        dto.getDeliverables().forEach(deliveabreID -> deliverables.add(deliveabreService.get(deliveabreID.getId())));
        post.setDeliverables(deliverables);*/

        post.setStatus(true);
        postRepo.save(post);
        return post;
    }

    @Override
    public Post update(int postID, PostDTO dto) {
        Post post = get(postID);
        List<Student> studentList = new ArrayList<>();
        dto.getStudentList().forEach(studentID -> studentList.add(studentService.get(studentID)));
        post.setStudentList(studentList);
        Locale locale = LocaleContextHolder.getLocale();
        if (ObjectUtils.isEmpty(dto.getTitle())) {
            throw new InvalidException(messageSource.getMessage("error.post.title-empty", null, locale));
        }
        if (ObjectUtils.isEmpty(dto.getDescription())) {
            throw new InvalidException(messageSource.getMessage("error.post.description-empty", null, locale));
        }

        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setPostType(dto.getPostType());
        post.setStartDate(dto.getStartDate());
        post.setEndDate(dto.getEndDate());
        post.setNumSlot(dto.getNumSlot());
        post.setLeader(dto.getLeader());



        List<Spectrum> spectrumList = new ArrayList<>();
        dto.getSpectrumList().forEach(spectrumID -> spectrumList.add(spectrumService.get(spectrumID.getId())));
        post.setSpectrumList(spectrumList);

        List<Student> queueList = new ArrayList<>();
        dto.getQueueList().forEach(studentID -> queueList.add(studentService.get(String.valueOf(studentID))));
        post.setQueueList(queueList);

        /*List<Deliverable> deliverables = new ArrayList<>();
        dto.getDeliverables().forEach(deliveabreID -> deliverables.add(deliveabreService.get(deliveabreID.getId())));
        post.setDeliverables(deliverables);*/

        post.setStatus(true);
        postRepo.save(post);
        return post;

    }

    @Override
    public Post delete(int id) {
        return null;
    }
}
