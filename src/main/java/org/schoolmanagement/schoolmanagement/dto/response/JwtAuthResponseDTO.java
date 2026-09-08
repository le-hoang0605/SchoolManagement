package org.schoolmanagement.schoolmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
