package org.schoolmanagement.schoolmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.AdminRegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.LoginRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.RegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.JwtAuthResponseDTO;
import org.schoolmanagement.schoolmanagement.dto.response.UserResponseDTO;
import org.schoolmanagement.schoolmanagement.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> studentRegister(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        UserResponseDTO newUser = authService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<UserResponseDTO> adminRegister(@Valid @RequestBody AdminRegisterRequestDTO adminRegisterRequestDTO) {
        UserResponseDTO newUser = authService.adminRegister(adminRegisterRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        JwtAuthResponseDTO jwt = authService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(jwt);
    }
}
