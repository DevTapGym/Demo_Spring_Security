package com.example.demo.Service;

import com.example.demo.DTO.Request.ReqUser;
import com.example.demo.DTO.Response.ResUser;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public ResUser getUserByUsername(String username) {
        User userExisting = userRepository.findByUsername(username).orElseThrow(()->
                new RuntimeException("User not found")
        );

        return userMapper.toResUser(userExisting);
    }

    public ResUser createUser(ReqUser reqUser) {
        if (userRepository.existsByUsername(reqUser.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(reqUser.getUsername())){
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findById(reqUser.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found"));

        User user = userMapper.toUser(reqUser);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(reqUser.getPassword()));
        userRepository.save(user);
        return userMapper.toResUser(user);
    }

}
