package com.example.demo.Mapper;

import com.example.demo.DTO.Request.ReqUser;
import com.example.demo.DTO.Response.ResUser;
import com.example.demo.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    User toUser(ReqUser reqUser);

    @Mapping(target = "role",source = "role.name")
    ResUser toResUser(User user);

    @Mapping(target = "role",source = "role.name")
    List<ResUser> toResUsers(List<User> users);
}
