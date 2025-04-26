package com.example.demo.Mapper;

import com.example.demo.DTO.Request.ReqRole;
import com.example.demo.DTO.Response.ResRole;
import com.example.demo.Entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "users", ignore = true)
    Role toRole(ReqRole reqRole);

    ResRole toResRole(Role role);

    List<ResRole> toResRoles(List<Role> roles);
}
