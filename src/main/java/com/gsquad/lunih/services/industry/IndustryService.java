package com.gsquad.lunih.services.industry;

import com.gsquad.lunih.dtos.categories.IndustryDTO;
import com.gsquad.lunih.entities.categories.Industry;

import java.util.List;

public interface IndustryService {

    //Page<PostType> listAllPaging(String search, int page, int size, String sort, String column);

    List<Industry> listAll();

    Industry get(int id);

    Industry create(IndustryDTO dto);

    Industry update(int id, IndustryDTO dto);

    Industry changeStatus(int id);

    Industry delete(int id);
}
