package com.example.demo.Service;

import com.example.demo.DTO.Request.ReqUser;
import com.example.demo.DTO.Response.ResUser;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Util.AppException;
import com.example.demo.Util.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public ResUser getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toResUser(user);
    }


    @PostAuthorize("returnObject.username == authentication.name")
    public ResUser getUserByUsername(String username) {
        User userExisting = userRepository.findByUsername(username).orElseThrow(()->
                new AppException(ErrorCode.USER_NOT_FOUND)
        );

        return userMapper.toResUser(userExisting);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<ResUser> getAllUsers() {
        return userMapper.toResUsers(userRepository.findAll());
    }

    public ResUser createUser(ReqUser reqUser) {
        if (userRepository.existsByUsername(reqUser.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(reqUser.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));

        User user = userMapper.toUser(reqUser);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(reqUser.getPassword()));

        userRepository.save(user);
        return userMapper.toResUser(user);
    }


}
