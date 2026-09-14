package org.schoolmanagement.schoolmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.LessonPreparationRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.LessonPreparationReviewRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.LessonPreparationResponseDTO;
import org.schoolmanagement.schoolmanagement.service.LessonPreparationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lesson-preparations")
@RequiredArgsConstructor
public class LessonPreparationController {
    private final LessonPreparationService lessonPreparationService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<LessonPreparationResponseDTO> createLessonPreparation(
            @Valid @RequestBody LessonPreparationRequestDTO requestDTO,
            Authentication authentication) {

        String currentUserEmail = authentication.getName();
        LessonPreparationResponseDTO response = lessonPreparationService.createLessonPreparation(requestDTO, currentUserEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/review")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    public ResponseEntity<LessonPreparationResponseDTO> reviewLessonPreparation(
            @PathVariable Integer id,
            @Valid @RequestBody LessonPreparationReviewRequestDTO requestDTO,
            Authentication authentication) {

        String currentUserEmail = authentication.getName();
        LessonPreparationResponseDTO response = lessonPreparationService.reviewLessonPreparation(id, requestDTO, currentUserEmail);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
