package com.gsquad.lunih.services.role;

import com.gsquad.lunih.dtos.RoleDTO;
import com.gsquad.lunih.entities.Role;
import com.gsquad.lunih.repos.RoleRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public List<Role> listAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role get(int id) {
        return roleRepo.findById(id).get();
    }

    @Override
    public Role create(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        roleRepo.save(role);
        return role;
    }

    @Override
    public Role update(int id, RoleDTO dto) {
        Role role = get(id);
        role.setName(dto.getName());
        roleRepo.save(role);
        return role;
    }

    @Override
    public Role delete(int id) {
        Role role = get(id);
        roleRepo.delete(role);
        return role;
    }
}
