package org.schoolmanagement.schoolmanagement.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.AssignTeacherCourseRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.TeacherCourseResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.*;
import org.schoolmanagement.schoolmanagement.exception.BadRequestException;
import org.schoolmanagement.schoolmanagement.exception.ResourceNotFoundException;
import org.schoolmanagement.schoolmanagement.repository.*;
import org.schoolmanagement.schoolmanagement.service.TeacherCourseService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherCourseServiceImpl implements TeacherCourseService {
    private final TeacherCourseRepository teacherCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    @Transactional
    public TeacherCourseResponseDTO assignTeacherToCourse(AssignTeacherCourseRequestDTO dto) {
        User teacher = userRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + dto.getTeacherId()));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + dto.getCourseId()));

        Section section = sectionRepository.findById(dto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + dto.getSectionId()));

        AcademicYear academicYear = academicYearRepository.findById(dto.getAcademicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("Academic Year not found with id: " + dto.getAcademicYearId()));

        if (teacherCourseRepository.existsByCourseIdAndSectionIdAndAcademicYearId(
                dto.getCourseId(), dto.getSectionId(), dto.getAcademicYearId())) {
            throw new BadRequestException("Course already assigned to a teacher in this section and academic year!");
        }

        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setTeacher(teacher);
        teacherCourse.setCourse(course);
        teacherCourse.setSection(section);
        teacherCourse.setAcademicYear(academicYear);

        TeacherCourse saved = teacherCourseRepository.save(teacherCourse);

        return mapToDTO(saved);
    }

    private TeacherCourseResponseDTO mapToDTO(TeacherCourse tc) {
        return new TeacherCourseResponseDTO(
                tc.getId(),
                tc.getTeacher().getId(),
                tc.getTeacher().getFirstName() + " " + tc.getTeacher().getLastName(),
                tc.getCourse().getId(),
                tc.getCourse().getName(),
                tc.getSection().getId(),
                tc.getSection().getName(),
                tc.getAcademicYear().getId(),
                tc.getAcademicYear().getName(),
                tc.getCreatedAt()
        );
    }
}
