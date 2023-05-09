package com.gsquad.lunih.services.program;

import com.gsquad.lunih.dtos.categories.ProgramDTO;
import com.gsquad.lunih.entities.categories.Program;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProgramService {

    Page<Program> listAllPaging(String search, int page, int size, String sort, String column);

    List<Program> listAll();

    Program get(int id);

    Program create(ProgramDTO dto);

    Program update(int id, ProgramDTO dto);

    Program changeStatus(int id);

    String delete(int id);

}
