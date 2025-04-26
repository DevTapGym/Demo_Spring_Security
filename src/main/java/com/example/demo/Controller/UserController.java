package com.example.demo.Controller;

import com.example.demo.DTO.Request.ReqUser;
import com.example.demo.DTO.Response.ApiResponse;
import com.example.demo.DTO.Response.ResUser;
import com.example.demo.Service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<ResUser>> getUserByUsername(@RequestBody ReqUser reqUser) {
        ResUser response = userService.getUserByUsername(reqUser.getUsername());
        ApiResponse<ResUser> apiResponse = ApiResponse.<ResUser>builder()
                .status(HttpStatus.CREATED.value())
                .message("Data fetched successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResUser>> createUser(@RequestBody ReqUser reqUser) {
        ResUser response = userService.createUser(reqUser);
        ApiResponse<ResUser> apiResponse = ApiResponse.<ResUser>builder()
                .status(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
