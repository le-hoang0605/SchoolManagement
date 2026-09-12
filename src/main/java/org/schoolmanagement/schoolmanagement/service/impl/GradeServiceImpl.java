package org.schoolmanagement.schoolmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.GradeRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.GradeUpdateRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.GradeResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.Course;
import org.schoolmanagement.schoolmanagement.entity.Grade;
import org.schoolmanagement.schoolmanagement.entity.Section;
import org.schoolmanagement.schoolmanagement.entity.User;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Role;
import org.schoolmanagement.schoolmanagement.exception.BadRequestException;
import org.schoolmanagement.schoolmanagement.exception.ResourceNotFoundException;
import org.schoolmanagement.schoolmanagement.exception.SectionNotFoundException;
import org.schoolmanagement.schoolmanagement.exception.UserNotFoundException;
import org.schoolmanagement.schoolmanagement.repository.*;
import org.schoolmanagement.schoolmanagement.service.GradeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if (recordedUser.getRole() == Role.teacher) {
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

    @Override
    public Page<GradeResponseDTO> getGradesByStudentId(Integer studentId, String currentEmail, Pageable pageable) {
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + currentEmail));

        if (currentUser.getRole() == Role.student && !currentUser.getId().equals(studentId)) {
            throw new BadRequestException("You can only view your own grades!");
        }

        if (!userRepository.existsById(studentId)) {
            throw new UserNotFoundException("Student not found with id: " + studentId);
        }
        return gradeRepository.findByStudentId(studentId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public Page<GradeResponseDTO> getGradesBySectionAndCourse(Integer sectionId, Integer courseId, String currentEmail, Pageable pageable) {
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + currentEmail));

        if (!sectionRepository.existsById(sectionId)) {
            throw new SectionNotFoundException("Section not found with id: " + sectionId);
        }

        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }

        if (currentUser.getRole() == Role.teacher) {
            boolean isAssigned = teacherCourseRepository.existsByTeacherIdAndCourseIdAndSectionId(
                    currentUser.getId(), courseId, sectionId);
            if (!isAssigned) {
                throw new BadRequestException("You are not assigned to teach this course in this section!");
            }
        }
        return gradeRepository.findBySectionIdAndCourseId(sectionId, courseId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public GradeResponseDTO updateGrade(Integer id, GradeUpdateRequestDTO dto, String currentUserEmail) {
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + currentUserEmail));

        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + id));

        if (currentUser.getRole() == Role.teacher) {
            boolean isAssigned = teacherCourseRepository.existsByTeacherIdAndCourseIdAndSectionId(
                    currentUser.getId(), grade.getCourse().getId(), grade.getSection().getId());
            if (!isAssigned) {
                throw new BadRequestException("You are not assigned to teach this course in this section!");
            }
        }

        if (dto.getAssessmentType() != null) {
            grade.setAssessmentType(dto.getAssessmentType());
        }
        if (dto.getAssessmentName() != null) {
            grade.setAssessmentName(dto.getAssessmentName());
        }
        if (dto.getMaxScore() != null) {
            grade.setMaxScore(dto.getMaxScore());
        }
        if (dto.getScore() != null) {
            grade.setScore(dto.getScore());
        }
        if (dto.getGradeDate() != null) {
            grade.setGradeDate(dto.getGradeDate());
        }
        if (dto.getNotes() != null) {
            grade.setNotes(dto.getNotes());
        }
        BigDecimal currentMaxScore = grade.getMaxScore() != null ? grade.getMaxScore() : new BigDecimal("100.00");
        if (grade.getScore().compareTo(BigDecimal.ZERO) < 0 || grade.getScore().compareTo(currentMaxScore) > 0) {
            throw new BadRequestException("Score must be between 0.00 and " + currentMaxScore);
        }

        Grade updatedGrade = gradeRepository.save(grade);
        return mapToDTO(updatedGrade);
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
