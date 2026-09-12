package org.schoolmanagement.schoolmanagement.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.AssessmentType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class GradeRequestDTO {
    @NotNull
    private Integer studentId;

    @NotNull
    private Integer courseId;

    @NotNull
    private Integer sectionId;

    private AssessmentType assessmentType = AssessmentType.exam;

    private String assessmentName;

    @NotNull
    @DecimalMin(value = "0.00", message = "Score must be greater than or equal to 0")
    private BigDecimal score;

    private BigDecimal maxScore;

    private LocalDate gradeDate;

    private String notes;
}
