package org.schoolmanagement.schoolmanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.schoolmanagement.schoolmanagement.dto.request.AdminRegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.RegisterRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.UserResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.Role;
import org.schoolmanagement.schoolmanagement.entity.User;
import org.schoolmanagement.schoolmanagement.exception.EmailExistsException;
import org.schoolmanagement.schoolmanagement.repository.UserRepository;
import org.schoolmanagement.schoolmanagement.service.impl.AuthServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequestDTO registerRequestDTO;
    private AdminRegisterRequestDTO adminRegisterRequestDTO;

    @BeforeEach
    void setUp() {
        registerRequestDTO = new RegisterRequestDTO();
        adminRegisterRequestDTO = new AdminRegisterRequestDTO();
    }

    //    STUDEN REGISTER
    @Test
    @DisplayName("Register Student - success")
    void studentRegisterSuccess() {
        registerRequestDTO.setFirstName("John");
        registerRequestDTO.setLastName("Doe");
        registerRequestDTO.setEmail("john.doe@omk.edu");
        registerRequestDTO.setPassword("password123");

        given(userRepository.existsByEmail(registerRequestDTO.getEmail())).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");
        given(userRepository.findMaxUserIdNumberByPrefix("STU2026")).willReturn(Optional.of("STU20260005"));

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setFirstName(registerRequestDTO.getFirstName());
        savedUser.setLastName(registerRequestDTO.getLastName());
        savedUser.setEmail(registerRequestDTO.getEmail());
        savedUser.setRole(Role.student);
        savedUser.setUserIdNumber("STU20260006");

        given(userRepository.save(any(User.class))).willReturn(savedUser);

        UserResponseDTO response = authService.register(registerRequestDTO);

        assertNotNull(response);
        assertEquals("John Doe", response.getFullName());
        assertEquals("john.doe@omk.edu", response.getEmail());
        assertEquals(Role.student, response.getRole());
        assertEquals("STU20260006", response.getUserIdNumber());
    }

    @Test
    @DisplayName("Register Student - fail")
    void register_ThrowsEmailExistsException() {
        registerRequestDTO.setFirstName("John");
        registerRequestDTO.setLastName("Doe");
        registerRequestDTO.setEmail("john.doe@omk.edu");
        registerRequestDTO.setPassword("password123");
        given(userRepository.existsByEmail(registerRequestDTO.getEmail())).willReturn(true);

        assertThrows(EmailExistsException.class, () -> authService.register(registerRequestDTO));

        verify(userRepository, never()).save(any(User.class));
    }

    //ADMIN REGISTER
    @Test
    @DisplayName("Admin Register - success")
    void adminRegisterSuccess() {
        adminRegisterRequestDTO.setFirstName("Sarah");
        adminRegisterRequestDTO.setLastName("Connor");
        adminRegisterRequestDTO.setEmail("sarah.teacher@omk.edu");
        adminRegisterRequestDTO.setPassword("teacherSecret123");
        adminRegisterRequestDTO.setRole(Role.teacher);

        given(userRepository.existsByEmail(adminRegisterRequestDTO.getEmail())).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");
        given(userRepository.findMaxUserIdNumberByPrefix("TCH2026")).willReturn(Optional.empty());

        User savedUser = new User();
        savedUser.setId(2);
        savedUser.setFirstName(adminRegisterRequestDTO.getFirstName());
        savedUser.setLastName(adminRegisterRequestDTO.getLastName());
        savedUser.setEmail(adminRegisterRequestDTO.getEmail());
        savedUser.setRole(Role.teacher);
        savedUser.setUserIdNumber("TCH20260001");

        given(userRepository.save(any(User.class))).willReturn(savedUser);

        UserResponseDTO response = authService.adminRegister(adminRegisterRequestDTO);

        assertNotNull(response);
        assertEquals("Sarah Connor", response.getFullName());
        assertEquals(Role.teacher, response.getRole());
        assertEquals("TCH20260001", response.getUserIdNumber());
    }

    @Test
    @DisplayName("Admin Tạo User - Thất bại do trùng Email")
    void createUserByAdmin_ThrowsEmailExistsException() {
        given(userRepository.existsByEmail(adminRegisterRequestDTO.getEmail())).willReturn(true);

        assertThrows(EmailExistsException.class, () -> authService.adminRegister(adminRegisterRequestDTO));

        verify(userRepository, never()).save(any(User.class));
    }
}
