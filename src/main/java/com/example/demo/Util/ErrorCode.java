package com.example.demo.Util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    // 400 Bad Request
    INVALID_REQUEST(40001, "Invalid request", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(40002, "Validation failed", HttpStatus.BAD_REQUEST),
    DUPLICATE_RESOURCE(40003, "Resource already exists", HttpStatus.BAD_REQUEST),

    // 401 Unauthorized
    UNAUTHORIZED(40101, "Unauthorized access", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(40102, "Invalid token", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN(40103, "Token expired", HttpStatus.UNAUTHORIZED),

    // 403 Forbidden
    FORBIDDEN(40301, "Access is denied", HttpStatus.FORBIDDEN),

    // 404 Not Found
    RESOURCE_NOT_FOUND(40401, "Resource not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(40402, "User not found", HttpStatus.NOT_FOUND),

    // 409 Conflict
    CONFLICT(40901, "Conflict detected", HttpStatus.CONFLICT),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(50001, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR(50002, "Database error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE(50301, "Service temporarily unavailable", HttpStatus.SERVICE_UNAVAILABLE);

    private int code;
    private String message;
    private HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
