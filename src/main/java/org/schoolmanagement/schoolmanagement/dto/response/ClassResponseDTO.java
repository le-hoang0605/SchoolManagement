package org.schoolmanagement.schoolmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassResponseDTO {
    private Integer id;
    private String name;
    private Integer gradeLevel;
    private Integer academicYearId;
    private String academicYearName;
    private LocalDateTime createdAt;
}
