package org.schoolmanagement.schoolmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.AdminRegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.ChangePasswordRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.LoginRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.RegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.JwtAuthResponseDTO;
import org.schoolmanagement.schoolmanagement.dto.response.UserResponseDTO;
import org.schoolmanagement.schoolmanagement.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        UserResponseDTO currentUser = authService.getCurrentUser(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(currentUser);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(Authentication authentication,
                                                              @Valid @RequestBody ChangePasswordRequestDTO passwordDTO) {
        authService.changePassword(passwordDTO, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Password changed successfully!"));
    }
}
