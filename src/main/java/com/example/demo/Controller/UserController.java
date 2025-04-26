package com.example.demo.Controller;

import com.example.demo.DTO.Request.ReqUser;
import com.example.demo.DTO.Response.ApiResponse;
import com.example.demo.DTO.Response.ResUser;
import com.example.demo.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @GetMapping("/my-info")
    public ResponseEntity<ApiResponse<ResUser>> getMyInfo() {
        ResUser response = userService.getMyInfo();

        ApiResponse<ResUser> apiResponse = ApiResponse.<ResUser>builder()
                .status(HttpStatus.OK.value())
                .message("Data fetched successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ResUser>>> getAllUsers() {
        List<ResUser> response = userService.getAllUsers();

        ApiResponse<List<ResUser>> apiResponse = ApiResponse.<List<ResUser>>builder()
                .status(HttpStatus.OK.value())
                .message("Data fetched successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<ApiResponse<ResUser>> getUserByUsername(@PathVariable String userName) {
        ResUser response = userService.getUserByUsername(userName);
        var auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("UserName: {}", auth.getName());
        log.info("Role: {}", auth.getAuthorities().toString());

        ApiResponse<ResUser> apiResponse = ApiResponse.<ResUser>builder()
                .status(HttpStatus.OK.value())
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
