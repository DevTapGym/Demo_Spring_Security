package com.example.demo.Mapper;

import com.example.demo.DTO.Request.ReqUser;
import com.example.demo.DTO.Response.ResUser;
import com.example.demo.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    User toUser(ReqUser reqUser);

    ResUser toResUser(User user);
}
