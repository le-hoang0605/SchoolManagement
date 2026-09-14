package org.schoolmanagement.schoolmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.request.ProgramRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.ProgramResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.*;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Role;
import org.schoolmanagement.schoolmanagement.exception.*;
import org.schoolmanagement.schoolmanagement.repository.*;
import org.schoolmanagement.schoolmanagement.service.ProgramService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    public Page<ProgramResponseDTO> getProgramsBySectionId(Integer sectionId, Pageable pageable) {
        if (!sectionRepository.existsById(sectionId)) {
            throw new SectionNotFoundException("Section not found with id: " + sectionId);
        }

        return programRepository.findBySectionId(sectionId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public Page<ProgramResponseDTO> getProgramsByTeacherId(Integer teacherId, Pageable pageable) {
        if (!userRepository.existsById(teacherId)) {
            throw new UserNotFoundException("Teacher not found with id: " + teacherId);
        }

        return programRepository.findByTeacherId(teacherId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional
    public ProgramResponseDTO createProgram(ProgramRequestDTO dto) {
        if (!dto.getStartTime().isBefore(dto.getEndTime())) {
            throw new BadRequestException("Start time must be before end time!");
        }

        Section section = sectionRepository.findById(dto.getSectionId())
                .orElseThrow(() -> new SectionNotFoundException("Section not found with id: " + dto.getSectionId()));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + dto.getCourseId()));

        User teacher = userRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new UserNotFoundException("Teacher not found with id: " + dto.getTeacherId()));

        if (teacher.getRole() != Role.teacher) {
            throw new BadRequestException("Assigned user must have teacher role!");
        }

        AcademicYear academicYear = null;
        if (dto.getAcademicYearId() != null) {
            academicYear = academicYearRepository.findById(dto.getAcademicYearId())
                    .orElseThrow(() -> new ResourceNotFoundException("Academic year not found with id: " + dto.getAcademicYearId()));
        }

        boolean isTeacherConflicted = programRepository.existsTeacherScheduleConflict(
                dto.getTeacherId(), dto.getDayOfWeek(), dto.getAcademicYearId(), dto.getStartTime(), dto.getEndTime());
        if (isTeacherConflicted) {
            throw new ConflictException(String.format(
                    "Teacher Conflict: Teacher '%s %s' is already assigned to another class on %s from %s to %s.",
                    teacher.getFirstName(), teacher.getLastName(), dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime()));
        }

        if (dto.getRoom() != null && !dto.getRoom().trim().isEmpty()) {
            String roomName = dto.getRoom().trim();
            boolean isRoomConflicted = programRepository.existsRoomScheduleConflict(
                    roomName, dto.getDayOfWeek(), dto.getAcademicYearId(), dto.getStartTime(), dto.getEndTime());
            if (isRoomConflicted) {
                throw new ConflictException(String.format(
                        "Room Conflict: Room '%s' is already occupied on %s from %s to %s.",
                        roomName, dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime()));
            }
        }

        Program program = new Program();
        program.setSection(section);
        program.setCourse(course);
        program.setTeacher(teacher);
        program.setDayOfWeek(dto.getDayOfWeek());
        program.setStartTime(dto.getStartTime());
        program.setEndTime(dto.getEndTime());
        program.setRoom(dto.getRoom() != null ? dto.getRoom().trim() : null);
        program.setAcademicYear(academicYear);

        Program saved = programRepository.save(program);
        return mapToDTO(saved);
    }

    private ProgramResponseDTO mapToDTO(Program p) {
        return new ProgramResponseDTO(
                p.getId(),
                p.getSection().getId(),
                p.getSection().getName(),
                p.getCourse().getId(),
                p.getCourse().getName(),
                p.getTeacher().getId(),
                p.getTeacher().getFirstName() + " " + p.getTeacher().getLastName(),
                p.getDayOfWeek(),
                p.getStartTime(),
                p.getEndTime(),
                p.getRoom(),
                p.getAcademicYear() != null ? p.getAcademicYear().getId() : null,
                p.getAcademicYear() != null ? p.getAcademicYear().getName() : null
        );
    }
}
