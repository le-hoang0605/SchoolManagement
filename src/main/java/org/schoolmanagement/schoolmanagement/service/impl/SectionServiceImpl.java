package org.schoolmanagement.schoolmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.response.StudentResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.StudentSection;
import org.schoolmanagement.schoolmanagement.entity.User;
import org.schoolmanagement.schoolmanagement.exception.SectionNotFoundException;
import org.schoolmanagement.schoolmanagement.repository.SectionRepository;
import org.schoolmanagement.schoolmanagement.repository.StudentSectionRepository;
import org.schoolmanagement.schoolmanagement.service.SectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final StudentSectionRepository studentSectionRepository;

    @Override
    public Page<StudentResponseDTO> getStudentsBySectionId(Integer sectionId, Pageable pageable) {
        if (!sectionRepository.existsById(sectionId))
            throw new SectionNotFoundException("Section not found with id: " + sectionId);

        return studentSectionRepository.findBySectionId(sectionId, pageable)
                .map(this::mapToDTO);
    }

    private StudentResponseDTO mapToDTO(StudentSection studentSection) {
        User student = studentSection.getStudent();
        return new StudentResponseDTO(
                student.getId(),
                student.getUserIdNumber(),
                student.getFirstName() + " " + student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getGender(),
                student.getAvatar(),
                studentSection.getEnrolledAt()
        );
    }
}
