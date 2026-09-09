package org.schoolmanagement.schoolmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.response.StudentResponseDTO;
import org.schoolmanagement.schoolmanagement.service.SectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @GetMapping("/{sectionId}/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR', 'TEACHER')")
    public ResponseEntity<Page<StudentResponseDTO>> getStudentsBySection(
            @PathVariable Integer sectionId,
            Pageable pageable) {

        Page<StudentResponseDTO> students = sectionService.getStudentsBySectionId(sectionId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }
}
