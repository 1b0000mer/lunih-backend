package com.gsquad.lunih.services.post_type;

import com.gsquad.lunih.dtos.categories.PostTypeDTO;
import com.gsquad.lunih.entities.categories.PostType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostTypeService {

    Page<PostType> listAllPaging(String search, int page, int size, String sort, String column);

    List<PostType> listAll();

    PostType get(int id);

    PostType create(PostTypeDTO dto);

    PostType update(int id, PostTypeDTO dto);

    PostType changeStatus(int id);

    PostType delete(int id);
}
