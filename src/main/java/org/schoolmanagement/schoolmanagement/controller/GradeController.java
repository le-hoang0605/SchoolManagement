package org.schoolmanagement.schoolmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.GradeRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.GradeResponseDTO;
import org.schoolmanagement.schoolmanagement.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<GradeResponseDTO> assignGrade(Authentication authentication,
                                                        @Valid @RequestBody GradeRequestDTO gradeRequestDTO) {
        String currentEmail = authentication.getName();
        GradeResponseDTO gradeResponseDTO = gradeService.assignGrade(gradeRequestDTO, currentEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(gradeResponseDTO);
    }
}
