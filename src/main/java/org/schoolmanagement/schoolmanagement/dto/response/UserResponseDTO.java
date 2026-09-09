package org.schoolmanagement.schoolmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Gender;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private int id;
    private String fullName;
    private String email;
    private Role role;
    private String userIdNumber;
    private String phone;
    private Gender gender;
    private String avatar;
    private LocalDate dateOfBirth;
    private String address;
    private LocalDateTime createdAt;
}
