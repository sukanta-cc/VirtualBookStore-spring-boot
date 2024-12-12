package com.virtualbookstore.VirtualBookStore.controllers;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtualbookstore.VirtualBookStore.Dtos.user.LoginDto;
import com.virtualbookstore.VirtualBookStore.Dtos.user.RegisterDto;
import com.virtualbookstore.VirtualBookStore.config.ApiResponse;
import com.virtualbookstore.VirtualBookStore.models.User;
import com.virtualbookstore.VirtualBookStore.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody RegisterDto registerDto) {
        ApiResponse<User> apiResponse = userService.register(registerDto);

        if (apiResponse.isError()) {
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody(required = true) LoginDto loginDto) {
        ApiResponse<String> apiResponse = userService.login(loginDto);

        if (apiResponse.isError()) {
            if (Objects.equals(apiResponse.getCode(), "USER_NOT_FOUND")) {
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            } else if (Objects.equals(apiResponse.getCode(), "INVALID_TOKEN")) {
                return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
    }

}
