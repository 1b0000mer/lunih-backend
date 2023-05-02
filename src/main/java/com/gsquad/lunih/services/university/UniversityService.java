package com.gsquad.lunih.services.university;

import com.gsquad.lunih.dtos.accountDTO.UniversityAccountDTO;
import com.gsquad.lunih.entities.University;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface UniversityService {

    Page<University> listAllPaging(String search, int page, int size, String sort, String column);

    List<University> listAll();

    University get(int id);

    University getCurrent(Principal principal);

    University update(int id, UniversityAccountDTO dto);

    University delete(int id);
}
