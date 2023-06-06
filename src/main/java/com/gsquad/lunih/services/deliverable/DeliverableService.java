package com.gsquad.lunih.services.deliverable;

import com.gsquad.lunih.dtos.PostDTO;
import com.gsquad.lunih.dtos.deliverables.ChangeStatusDTO;
import com.gsquad.lunih.dtos.deliverables.DeliverableDTO;
import com.gsquad.lunih.entities.Deliverable;
import com.gsquad.lunih.entities.Post;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface DeliverableService {

    Page<Deliverable> listAllPaging(String search, int page, int size, String sort, String column);

    List<Deliverable> listAll();

    Deliverable get(int id);

    Deliverable create(DeliverableDTO dto);

    Deliverable update(int id, DeliverableDTO dto);

    Deliverable changeStatus(int id, ChangeStatusDTO dto);

    Deliverable delete(int id);
}

