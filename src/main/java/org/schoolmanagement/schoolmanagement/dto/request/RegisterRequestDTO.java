package org.schoolmanagement.schoolmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Gender;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email format")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Size(max = 30)
    private String phone;

    private Gender gender;

    private String avatar;

    private LocalDate dateOfBirth;

    private String address;

}
