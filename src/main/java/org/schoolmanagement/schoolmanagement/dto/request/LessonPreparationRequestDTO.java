package org.schoolmanagement.schoolmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LessonPreparationRequestDTO {
    private Integer courseId;

    private Integer sectionId;

    @NotBlank
    private String title;

    private String content;

    private String filePath;

    private String fileName;
}
