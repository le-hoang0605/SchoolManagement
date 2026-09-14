package org.schoolmanagement.schoolmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.LessonPreparationStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonPreparationResponseDTO {
    private Integer id;
    private Integer teacherId;
    private String teacherName;
    private Integer courseId;
    private String courseName;
    private Integer sectionId;
    private String sectionName;
    private String title;
    private String content;
    private String filePath;
    private String fileName;
    private LessonPreparationStatus status;
    private String adminNotes;
    private Integer reviewedById;
    private String reviewedByName;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
