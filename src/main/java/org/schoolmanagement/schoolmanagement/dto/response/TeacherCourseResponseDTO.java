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
public class TeacherCourseResponseDTO {
    private Integer id;
    private Integer teacherId;
    private String teacherName;
    private Integer courseId;
    private String courseName;
    private Integer sectionId;
    private String sectionName;
    private Integer academicYearId;
    private String academicYearName;
    private LocalDateTime createdAt;
}
