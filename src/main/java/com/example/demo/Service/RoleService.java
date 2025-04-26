package com.example.demo.Service;

import com.example.demo.DTO.Request.ReqRole;
import com.example.demo.DTO.Response.ResRole;
import com.example.demo.Entity.Role;
import com.example.demo.Enums.ERole;
import com.example.demo.Mapper.RoleMapper;
import com.example.demo.Repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public List<ResRole> getAllRoles() {
        return roleMapper.toResRoles(roleRepository.findAll());
    }

    public ResRole createRole(ReqRole reqRole) {
        String roleName = reqRole.getName();

        if(roleRepository.existsByName(roleName)) {
            throw new RuntimeException("Role name already exists");
        }

        try {
            ERole.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role name: " + roleName);
        }

        Role role = roleMapper.toRole(reqRole);

        if (role.getName() == null) {
            throw new RuntimeException("Role name cannot be null");
        }

        roleRepository.save(role);
        return roleMapper.toResRole(role);
    }
}
