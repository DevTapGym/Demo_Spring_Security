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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResRole>>> getAllRoles() {
        List<ResRole> roles = roleService.getAllRoles();

        ApiResponse<List<ResRole>> apiResponse = ApiResponse.<List<ResRole>>builder()
                .status(HttpStatus.OK.value())
                .message("Fetch data successfully")
                .data(roles)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

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
