package org.schoolmanagement.schoolmanagement.service;

import org.schoolmanagement.schoolmanagement.dto.request.GradeRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.GradeResponseDTO;

public interface GradeService {
    GradeResponseDTO assignGrade(GradeRequestDTO gradeRequestDTO, String currentEmail);
}
