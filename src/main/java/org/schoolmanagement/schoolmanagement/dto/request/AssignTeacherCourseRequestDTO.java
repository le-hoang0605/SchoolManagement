package org.schoolmanagement.schoolmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignTeacherCourseRequestDTO {
    @NotNull
    private Integer teacherId;

    @NotNull
    private Integer courseId;

    @NotNull
    private Integer sectionId;

    @NotNull
    private Integer academicYearId;
}
