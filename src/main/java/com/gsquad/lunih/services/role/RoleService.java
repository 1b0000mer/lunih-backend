package com.gsquad.lunih.services.role;

import com.gsquad.lunih.dtos.RoleDTO;
import com.gsquad.lunih.entities.Role;

import java.util.List;

public interface RoleService {

    List<Role> listAll();

    Role get(int id);

    Role create(RoleDTO dto);

    Role update(int id, RoleDTO dto);

    Role delete(int id);
}
