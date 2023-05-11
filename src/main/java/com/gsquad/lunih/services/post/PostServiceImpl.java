package com.gsquad.lunih.services.post;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.entities.Post;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.repos.PostRepo;
import com.gsquad.lunih.services.student.StudentService;
import org.springframework.data.domain.Page;
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
    public Page<Post> listAllPaging(String search, int page, int size, String sort, String column) {
        return null;
    }

    @Override
    public List<Post> listAll() {
        return null;
    }

    @Override
    public Post get(int id) {
        return null;
    }

    @Override
    public Post create(PostDTO dto) {
        return null;
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
