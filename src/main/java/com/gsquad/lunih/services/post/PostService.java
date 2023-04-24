package com.gsquad.lunih.services.post;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.entities.Post;

import java.util.List;

public interface PostService {

    List<Post> listAll();

    Post get(int id);

    Post create(PostDTO dto);

    Post update(int id, PostDTO dto);

    Post delete(int id);
}
