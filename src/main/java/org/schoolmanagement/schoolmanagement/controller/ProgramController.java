package org.schoolmanagement.schoolmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.ProgramRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.ProgramResponseDTO;
import org.schoolmanagement.schoolmanagement.service.ProgramService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<Page<ProgramResponseDTO>> getProgramsBySectionId(
            @PathVariable Integer sectionId,
            Pageable pageable
    ) {
        Page<ProgramResponseDTO> response = programService.getProgramsBySectionId(sectionId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<Page<ProgramResponseDTO>> getProgramsByTeacherId(
            @PathVariable Integer teacherId,
            Pageable pageable) {

        Page<ProgramResponseDTO> response = programService.getProgramsByTeacherId(teacherId, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    public ResponseEntity createProgram(@Valid @RequestBody ProgramRequestDTO requestDTO) {
        ProgramResponseDTO response = programService.createProgram(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
