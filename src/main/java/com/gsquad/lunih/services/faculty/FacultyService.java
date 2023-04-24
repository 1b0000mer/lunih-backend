package com.gsquad.lunih.services.faculty;

import com.gsquad.lunih.dtos.categories.FacultyDTO;
import com.gsquad.lunih.entities.categories.Faculty;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FacultyService {

    Page<Faculty> listAllPaging(String search, int page, int size, String sort, String column);

    List<Faculty> listAll();

    Faculty get(int id);

    Faculty create(FacultyDTO dto);

    Faculty update(int id, FacultyDTO dto);

    Faculty changeStatus(int id);

    Faculty delete(int id);

}
