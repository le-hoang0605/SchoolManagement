package org.schoolmanagement.schoolmanagement.service;

import org.schoolmanagement.schoolmanagement.dto.request.GradeRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.GradeUpdateRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.GradeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GradeService {
    GradeResponseDTO assignGrade(GradeRequestDTO gradeRequestDTO, String currentEmail);

    Page<GradeResponseDTO> getGradesByStudentId(Integer studentId, String currentEmail, Pageable pageable);

    Page<GradeResponseDTO> getGradesBySectionAndCourse(Integer sectionId, Integer courseId, String currentEmail, Pageable pageable);

    GradeResponseDTO updateGrade(Integer id, GradeUpdateRequestDTO dto, String currentEmail);
}
