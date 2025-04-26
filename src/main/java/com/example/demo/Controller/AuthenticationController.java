package com.example.demo.Controller;

import com.example.demo.DTO.Request.ReqAuthentication;
import com.example.demo.DTO.Request.ReqIntrospect;
import com.example.demo.DTO.Response.ApiResponse;
import com.example.demo.DTO.Response.ResAuthentication;
import com.example.demo.DTO.Response.ResIntrospect;
import com.example.demo.DTO.Response.ResRole;
import com.example.demo.Service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ResponseEntity<ApiResponse<ResAuthentication>> login(@RequestBody ReqAuthentication authentication) {
        ResAuthentication resAuthentication = authenticationService.authenticate(authentication);

        ApiResponse<ResAuthentication> apiResponse = ApiResponse.<ResAuthentication>builder()
                .status(HttpStatus.CREATED.value())
                .message("Login " + (resAuthentication.isSuccess() ? "successful" : "failed"))
                .data(resAuthentication)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/introspect")
    ResponseEntity<ApiResponse<ResIntrospect>> introspect(@RequestBody ReqIntrospect request) throws ParseException, JOSEException {
        ResIntrospect result = authenticationService.introspect(request);

        ApiResponse<ResIntrospect> apiResponse = ApiResponse.<ResIntrospect>builder()
                .status(result.isValid() ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value())
                .message("Login " + (result.isValid() ? "successful" : "failed"))
                .data(result)
                .build();

        return ResponseEntity.status(result.isValid() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
