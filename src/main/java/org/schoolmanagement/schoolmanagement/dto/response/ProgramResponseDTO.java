package org.schoolmanagement.schoolmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.DayOfWeek;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramResponseDTO {
    private Integer id;
    private Integer sectionId;
    private String sectionName;
    private Integer courseId;
    private String courseName;
    private Integer teacherId;
    private String teacherName;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
    private Integer academicYearId;
    private String academicYearName;
}
