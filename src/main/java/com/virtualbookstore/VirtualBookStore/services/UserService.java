package com.virtualbookstore.VirtualBookStore.services;

import com.virtualbookstore.VirtualBookStore.Dtos.user.LoginDto;
import com.virtualbookstore.VirtualBookStore.Dtos.user.RegisterDto;
import com.virtualbookstore.VirtualBookStore.config.ApiResponse;
import com.virtualbookstore.VirtualBookStore.models.User;
import com.virtualbookstore.VirtualBookStore.repositories.UserRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepositories userRepositories;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(PasswordEncoder passwordEncoder, UserRepositories userRepositories,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepositories = userRepositories;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ApiResponse<User> register(RegisterDto userDto) {
        ApiResponse<User> response = new ApiResponse<>();
        try {
            // Check if user exist or not

            User existingUser = userRepositories.findByEmail(userDto.getEmail()).orElse(null);

            if (existingUser == null) {
                User user = new User();
                user.setName(userDto.getName());
                user.setEmail(userDto.getEmail());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setUsername(userDto.getEmail());

                User newUser = userRepositories.save(user);

                response.setError(false);
                response.setCode("USER_REGISTERED_SUCCESS");
                response.setMessage("User registered successfully");

                User userData = new User();
                userData.setId(newUser.getId());
                userData.setName(newUser.getName());
                userData.setEmail(newUser.getEmail());
                response.setData(userData);
            } else {
                response.setError(true);
                response.setCode("USER_ALREADY_EXISTS");
                response.setMessage("User already exists");
            }

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<String> login(LoginDto loginDto) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                            loginDto.getPassword()));
            // Find the user data
            User ExistingUser = userRepositories.findByEmail(loginDto.getEmail()).orElse(null);

            if (ExistingUser == null) {
                response.setError(true);
                response.setCode("USER_NOT_FOUND");
                response.setMessage("User not found");
            } else {
                // Generate token
                Map<String, Object> jwtPayload = new HashMap<>();

                jwtPayload.put("id", ExistingUser.getId());
                jwtPayload.put("email", ExistingUser.getEmail());

                String access_token = jwtService.generateToken(jwtPayload, ExistingUser);

                if (access_token.isEmpty()) {
                    response.setError(true);
                    response.setCode("INVALID_TOKEN");
                    response.setMessage("Invalid token");
                } else {
                    response.setError(false);
                    response.setCode("USER_LOGGED_IN_SUCCESS");
                    response.setMessage("User logged in success");
                    response.setData(access_token);
                }
            }

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
