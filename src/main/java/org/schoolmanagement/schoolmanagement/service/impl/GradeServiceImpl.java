package org.schoolmanagement.schoolmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.GradeRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.GradeResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.Course;
import org.schoolmanagement.schoolmanagement.entity.Grade;
import org.schoolmanagement.schoolmanagement.entity.Section;
import org.schoolmanagement.schoolmanagement.entity.User;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Role;
import org.schoolmanagement.schoolmanagement.exception.BadRequestException;
import org.schoolmanagement.schoolmanagement.exception.ResourceNotFoundException;
import org.schoolmanagement.schoolmanagement.repository.*;
import org.schoolmanagement.schoolmanagement.service.GradeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final TeacherCourseRepository teacherCourseRepository;

    @Override
    public GradeResponseDTO assignGrade(GradeRequestDTO gradeRequestDTO, String currentEmail) {
        User recordedUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Logged in user not found"));

        User student = userRepository.findById(gradeRequestDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + gradeRequestDTO.getStudentId()));

        Course course = courseRepository.findById(gradeRequestDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + gradeRequestDTO.getCourseId()));

        Section section = sectionRepository.findById(gradeRequestDTO.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + gradeRequestDTO.getSectionId()));

        if (recordedUser.getRole() != Role.teacher) {
            boolean isAssigned = teacherCourseRepository.existsByTeacherIdAndCourseIdAndSectionId(recordedUser.getId(), course.getId(), section.getId());
            if (!isAssigned)
                throw new BadRequestException("You are not assigned to teach this course in this section!");
        }

        BigDecimal maxScore = (gradeRequestDTO.getMaxScore() != null) ? gradeRequestDTO.getMaxScore() : new BigDecimal("100.00");
        if (gradeRequestDTO.getScore().compareTo(BigDecimal.ZERO) < 0 || gradeRequestDTO.getScore().compareTo(maxScore) > 0) {
            throw new BadRequestException("Score must be between 0.00 and " + maxScore);
        }

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setSection(section);
        grade.setAssessmentType(gradeRequestDTO.getAssessmentType());
        grade.setAssessmentName(gradeRequestDTO.getAssessmentName());
        grade.setScore(gradeRequestDTO.getScore());
        grade.setMaxScore(maxScore);
        grade.setGradeDate(gradeRequestDTO.getGradeDate());
        grade.setNotes(gradeRequestDTO.getNotes());
        grade.setRecordedBy(recordedUser);

        Grade savedGrade = gradeRepository.save(grade);
        return mapToDTO(savedGrade);
    }

    private GradeResponseDTO mapToDTO(Grade g) {
        return new GradeResponseDTO(
                g.getId(),
                g.getStudent().getId(),
                g.getStudent().getFirstName() + " " + g.getStudent().getLastName(),
                g.getStudent().getUserIdNumber(),
                g.getCourse().getId(),
                g.getCourse().getName(),
                g.getSection().getId(),
                g.getSection().getName(),
                g.getAssessmentType(),
                g.getAssessmentName(),
                g.getScore(),
                g.getMaxScore(),
                g.getGradeDate(),
                g.getNotes(),
                g.getRecordedBy() != null ? g.getRecordedBy().getId() : null,
                g.getRecordedBy() != null ? g.getRecordedBy().getFirstName() + " " + g.getRecordedBy().getLastName() : null,
                g.getCreatedAt()
        );
    }
}
