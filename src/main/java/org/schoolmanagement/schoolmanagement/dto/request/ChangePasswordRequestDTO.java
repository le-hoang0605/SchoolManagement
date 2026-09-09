package org.schoolmanagement.schoolmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDTO {
    @NotBlank(message = "Old password is required!")
    @Size(min = 6)
    private String oldPassword;

    @NotBlank(message = "New password is required!")
    @Size(min = 6)
    private String newPassword;
}
