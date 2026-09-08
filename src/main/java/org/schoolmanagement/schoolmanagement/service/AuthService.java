package org.schoolmanagement.schoolmanagement.service;

import org.schoolmanagement.schoolmanagement.dto.request.AdminRegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.LoginRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.RegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.JwtAuthResponseDTO;
import org.schoolmanagement.schoolmanagement.dto.response.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(RegisterRequestDTO requestDTO);

    UserResponseDTO adminRegister(AdminRegisterRequestDTO requestDTO);

    JwtAuthResponseDTO login(LoginRequestDTO loginRequestDTO);
}
