package org.schoolmanagement.schoolmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.AssignTeacherCourseRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.TeacherCourseResponseDTO;
import org.schoolmanagement.schoolmanagement.service.TeacherCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher-courses")
@RequiredArgsConstructor
public class TeacherCourseController {
    private final TeacherCourseService teacherCourseService;

    @PostMapping("/assign")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    public ResponseEntity<TeacherCourseResponseDTO> assignTeacher(
            @Valid @RequestBody AssignTeacherCourseRequestDTO requestDTO) {

        TeacherCourseResponseDTO response = teacherCourseService.assignTeacherToCourse(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
