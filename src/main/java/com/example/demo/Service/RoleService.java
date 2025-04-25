package com.example.demo.Service;

import com.example.demo.DTO.Request.ReqRole;
import com.example.demo.DTO.Response.ResRole;
import com.example.demo.Entity.Role;
import com.example.demo.Mapper.RoleMapper;
import com.example.demo.Repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public ResRole createRole(ReqRole reqRole) {
        if(roleRepository.existsByName(reqRole.getName())) {
            throw new RuntimeException("Role name already exists");
        }

        Role role = roleMapper.toRole(reqRole);

        // Đảm bảo gán name trước khi lưu
        if (role.getName() == null) {
            throw new RuntimeException("Role name cannot be null");
        }

        roleRepository.save(role);
        return roleMapper.toResRole(role);
    }
}
