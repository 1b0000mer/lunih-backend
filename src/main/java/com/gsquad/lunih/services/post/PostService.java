package com.gsquad.lunih.services.post;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.entities.Post;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface PostService {

    Page<Post> listAllPaging(String search, int page, int size, String sort, String column);

    List<Post> listAll();

    Post get(int id);

    Post create(PostDTO dto);

    Post update(int id, PostDTO dto);

    Post changeStatus(int id);

    Post delete(int id);

//    Post studentApplyPost(Principal principal, int id);

//    Post UniversityPublishPost(Principal principal, PostDTO dto);
}
