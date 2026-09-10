package org.schoolmanagement.schoolmanagement.service;

import org.schoolmanagement.schoolmanagement.dto.request.AssignTeacherCourseRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.TeacherCourseResponseDTO;

public interface TeacherCourseService {
    TeacherCourseResponseDTO assignTeacherToCourse(AssignTeacherCourseRequestDTO dto);
}
