package org.schoolmanagement.schoolmanagement.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.LessonPreparationRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.request.LessonPreparationReviewRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.LessonPreparationResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.Course;
import org.schoolmanagement.schoolmanagement.entity.LessonPreparation;
import org.schoolmanagement.schoolmanagement.entity.Section;
import org.schoolmanagement.schoolmanagement.entity.User;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.LessonPreparationStatus;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Role;
import org.schoolmanagement.schoolmanagement.exception.BadRequestException;
import org.schoolmanagement.schoolmanagement.exception.ResourceNotFoundException;
import org.schoolmanagement.schoolmanagement.exception.SectionNotFoundException;
import org.schoolmanagement.schoolmanagement.exception.UserNotFoundException;
import org.schoolmanagement.schoolmanagement.repository.CourseRepository;
import org.schoolmanagement.schoolmanagement.repository.LessonPreparationRepository;
import org.schoolmanagement.schoolmanagement.repository.SectionRepository;
import org.schoolmanagement.schoolmanagement.repository.UserRepository;
import org.schoolmanagement.schoolmanagement.service.LessonPreparationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LessonPreparationServiceImpl implements LessonPreparationService {
    private final LessonPreparationRepository lessonPreparationRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    @Override
    @Transactional
    public LessonPreparationResponseDTO createLessonPreparation(LessonPreparationRequestDTO dto, String currentUserEmail) {
        User teacher = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UserNotFoundException("Teacher not found with email: " + currentUserEmail));

        if (teacher.getRole() != Role.teacher) {
            throw new BadRequestException("Only teachers are allowed to create lesson preparations!");
        }

        Course course = null;
        if (dto.getCourseId() != null) {
            course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + dto.getCourseId()));
        }

        Section section = null;
        if (dto.getSectionId() != null) {
            section = sectionRepository.findById(dto.getSectionId())
                    .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + dto.getSectionId()));
        }

        LessonPreparation lessonPrep = new LessonPreparation();
        lessonPrep.setTeacher(teacher);
        lessonPrep.setCourse(course);
        lessonPrep.setSection(section);
        lessonPrep.setTitle(dto.getTitle());
        lessonPrep.setContent(dto.getContent());
        lessonPrep.setFilePath(dto.getFilePath());
        lessonPrep.setFileName(dto.getFileName());
        lessonPrep.setStatus(LessonPreparationStatus.pending);

        LessonPreparation saved = lessonPreparationRepository.save(lessonPrep);
        return mapToDTO(saved);
    }

    @Override
    @Transactional
    public LessonPreparationResponseDTO reviewLessonPreparation(
            Integer id, LessonPreparationReviewRequestDTO dto, String currentUserEmail) {
        User reviewer = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + currentUserEmail));

        LessonPreparation lessonPrep = lessonPreparationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson preparation not found with id: " + id));

        if (dto.getStatus() == LessonPreparationStatus.pending) {
            throw new BadRequestException("Review status must be approved, signed, or rejected!");
        }

        lessonPrep.setStatus(dto.getStatus());
        lessonPrep.setAdminNotes(dto.getAdminNotes());
        lessonPrep.setReviewedBy(reviewer);
        lessonPrep.setReviewedAt(LocalDateTime.now());

        LessonPreparation updated = lessonPreparationRepository.save(lessonPrep);
        return mapToDTO(updated);
    }

    private LessonPreparationResponseDTO mapToDTO(LessonPreparation lp) {
        return new LessonPreparationResponseDTO(
                lp.getId(),
                lp.getTeacher().getId(),
                lp.getTeacher().getFirstName() + " " + lp.getTeacher().getLastName(),
                lp.getCourse() != null ? lp.getCourse().getId() : null,
                lp.getCourse() != null ? lp.getCourse().getName() : null,
                lp.getSection() != null ? lp.getSection().getId() : null,
                lp.getSection() != null ? lp.getSection().getName() : null,
                lp.getTitle(),
                lp.getContent(),
                lp.getFilePath(),
                lp.getFileName(),
                lp.getStatus(),
                lp.getAdminNotes(),
                lp.getReviewedBy() != null ? lp.getReviewedBy().getId() : null,
                lp.getReviewedBy() != null ? lp.getReviewedBy().getFirstName() + " " + lp.getReviewedBy().getLastName() : null,
                lp.getReviewedAt(),
                lp.getCreatedAt(),
                lp.getUpdatedAt()
        );
    }
}
