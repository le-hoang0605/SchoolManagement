package org.schoolmanagement.schoolmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.DayOfWeek;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ProgramRequestDTO {
    @NotNull(message = "Section ID is required!")
    private Integer sectionId;

    @NotNull(message = "Course ID is required!")
    private Integer courseId;

    @NotNull(message = "Teacher ID is required!")
    private Integer teacherId;

    @NotNull(message = "Day of week is required!")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required!")
    private LocalTime startTime;

    @NotNull(message = "End time is required!")
    private LocalTime endTime;

    private String room;

    private Integer academicYearId;
}
