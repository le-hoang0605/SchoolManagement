package org.schoolmanagement.schoolmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Gender;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private Integer studentId;
    private String userIdNumber;
    private String fullName;
    private String email;
    private String phone;
    private Gender gender;
    private String avatar;
    private LocalDateTime enrolledAt;
}
