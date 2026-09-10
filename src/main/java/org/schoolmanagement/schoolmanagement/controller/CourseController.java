package org.schoolmanagement.schoolmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.response.CourseResponseDTO;
import org.schoolmanagement.schoolmanagement.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<Page<CourseResponseDTO>> getAllCourses(Pageable pageable) {
        Page<CourseResponseDTO> courses = courseService.getAllCourses(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }
}
