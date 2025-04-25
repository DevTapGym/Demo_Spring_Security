package com.example.demo.Controller;

import com.example.demo.DTO.Request.ReqRole;
import com.example.demo.DTO.Response.ApiResponse;
import com.example.demo.DTO.Response.ResRole;
import com.example.demo.Service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<ResRole>> createRole(@RequestBody ReqRole reqRole) {
        ResRole response = roleService.createRole(reqRole);

        ApiResponse<ResRole> apiResponse = ApiResponse.<ResRole>builder()
                .status(HttpStatus.CREATED.value())
                .message("Role created successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
