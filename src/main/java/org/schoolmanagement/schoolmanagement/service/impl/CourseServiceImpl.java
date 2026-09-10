package org.schoolmanagement.schoolmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.response.CourseResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.Course;
import org.schoolmanagement.schoolmanagement.repository.CourseRepository;
import org.schoolmanagement.schoolmanagement.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public Page<CourseResponseDTO> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    private CourseResponseDTO mapToDTO(Course course) {
        return new CourseResponseDTO(
                course.getId(),
                course.getName(),
                course.getCode(),
                course.getDescription(),
                course.getColor(),
                course.getCredits(),
                course.getCreatedAt()
        );
    }
}
