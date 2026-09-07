package org.schoolmanagement.schoolmanagement.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.RegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.entity.Role;
import org.schoolmanagement.schoolmanagement.entity.User;
import org.schoolmanagement.schoolmanagement.exception.EmailExistsException;
import org.schoolmanagement.schoolmanagement.repository.UserRepository;
import org.schoolmanagement.schoolmanagement.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserRepository register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByEmail(registerRequestDTO.getEmail()))
            throw new EmailExistsException("Email already exists!");
        var user = new User();
        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setPhone(registerRequestDTO.getPhone());
        user.setGender(registerRequestDTO.getGender());
        user.setAvatar(registerRequestDTO.getAvatar());
        user.setDateOfBirth(registerRequestDTO.getDateOfBirth());
        user.setAddress(registerRequestDTO.getAddress());

        user.setRole(Role.student);
        user.setUserIdNumber();

    }
}
