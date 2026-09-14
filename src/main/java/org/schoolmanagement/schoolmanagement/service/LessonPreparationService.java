package org.schoolmanagement.schoolmanagement.service;

import org.schoolmanagement.schoolmanagement.dto.request.LessonPreparationRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.LessonPreparationReviewRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.LessonPreparationResponseDTO;

public interface LessonPreparationService {
    LessonPreparationResponseDTO createLessonPreparation(LessonPreparationRequestDTO dto, String currentUserEmail);

    LessonPreparationResponseDTO reviewLessonPreparation(Integer id, LessonPreparationReviewRequestDTO dto, String currentUserEmail);
}
