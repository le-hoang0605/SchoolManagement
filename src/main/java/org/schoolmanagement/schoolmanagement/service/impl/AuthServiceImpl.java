package org.schoolmanagement.schoolmanagement.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.AdminRegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.RegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.UserResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.Role;
import org.schoolmanagement.schoolmanagement.entity.User;
import org.schoolmanagement.schoolmanagement.exception.EmailExistsException;
import org.schoolmanagement.schoolmanagement.repository.UserRepository;
import org.schoolmanagement.schoolmanagement.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) {
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
        user.setUserIdNumber(generateUserIdNumber(Role.student));

        User savedUser = userRepository.save(user);

        return mapToResponseDTO((savedUser));
    }

    @Override
    @Transactional
    public UserResponseDTO adminRegister(AdminRegisterRequestDTO adminRegisterRequestDTO) {
        if (userRepository.existsByEmail(adminRegisterRequestDTO.getEmail()))
            throw new EmailExistsException("Email already exists!");
        var user = new User();
        user.setFirstName(adminRegisterRequestDTO.getFirstName());
        user.setLastName(adminRegisterRequestDTO.getLastName());
        user.setEmail(adminRegisterRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(adminRegisterRequestDTO.getPassword()));
        user.setPhone(adminRegisterRequestDTO.getPhone());
        user.setGender(adminRegisterRequestDTO.getGender());
        user.setAvatar(adminRegisterRequestDTO.getAvatar());
        user.setDateOfBirth(adminRegisterRequestDTO.getDateOfBirth());
        user.setAddress(adminRegisterRequestDTO.getAddress());

        user.setRole(adminRegisterRequestDTO.getRole());
        user.setUserIdNumber(generateUserIdNumber(adminRegisterRequestDTO.getRole()));

        User savedUser = userRepository.save(user);

        return mapToResponseDTO((savedUser));
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getUserIdNumber(),
                user.getPhone(),
                user.getGender(),
                user.getAvatar(),
                user.getDateOfBirth(),
                user.getAddress(),
                user.getCreatedAt()
        );
    }

    private String generateUserIdNumber(Role role) {
        String rolePrefix = switch (role) {
            case admin -> "ADM";
            case coordinator -> "CRD";
            case teacher -> "TCH";
            case student -> "STU";
        };
        int currentYear = LocalDate.now().getYear();
        String prefix = rolePrefix + currentYear;
        String maxUserIdNumber = userRepository.findMaxUserIdNumberByPrefix(prefix).orElse(null);

        int nextSequence = 1;

        if (maxUserIdNumber != null) {
            String last4Digits = maxUserIdNumber.substring(maxUserIdNumber.length() - 4);
            nextSequence = Integer.parseInt(last4Digits) + 1;
        }
        return String.format("%s%04d", prefix, nextSequence);

    }
}
