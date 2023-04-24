package com.gsquad.lunih.services.post;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.entities.Post;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.repos.PostRepo;
import com.gsquad.lunih.services.student.StudentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    private final StudentService studentService;

    public PostServiceImpl(PostRepo postRepo, StudentService studentService) {
        this.postRepo = postRepo;
        this.studentService = studentService;
    }

    @Override
    public List<Post> listAll() {
        return postRepo.findAll();
    }

    @Override
    public Post get(int id) {
        return postRepo.findById(id).get();
    }

    @Override
    public Post create(PostDTO dto) {
        Post post = new Post();
        List<Student> studentList = new ArrayList<>();
        dto.getStudentList().forEach(studentID -> studentList.add(
                studentService.get(studentID)
        ));

        post.setStudentList(studentList);
        postRepo.save(post);
        return post;
    }

    @Override
    public Post update(int id, PostDTO dto) {
        return null;
    }

    @Override
    public Post delete(int id) {
        return null;
    }
}
