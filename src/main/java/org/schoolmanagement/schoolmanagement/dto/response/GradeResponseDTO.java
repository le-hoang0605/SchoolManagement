package org.schoolmanagement.schoolmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.AssessmentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponseDTO {
    private Integer id;
    private Integer studentId;
    private String studentName;
    private String studentUserIdNumber;
    private Integer courseId;
    private String courseName;
    private Integer sectionId;
    private String sectionName;
    private AssessmentType assessmentType;
    private String assessmentName;
    private BigDecimal score;
    private BigDecimal maxScore;
    private LocalDate gradeDate;
    private String notes;
    private Integer recordedById;
    private String recordedByName;
    private LocalDateTime createdAt;
}
