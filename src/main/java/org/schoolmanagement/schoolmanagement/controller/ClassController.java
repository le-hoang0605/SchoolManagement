package org.schoolmanagement.schoolmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.response.ClassResponseDTO;
import org.schoolmanagement.schoolmanagement.service.ClassService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping
    public ResponseEntity<Page<ClassResponseDTO>> getAllClasses(Pageable pageable) {
        Page<ClassResponseDTO> allClasses = classService.getAllClasses(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allClasses);
    }
}
