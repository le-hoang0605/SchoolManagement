package org.schoolmanagement.schoolmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.schoolmanagement.schoolmanagement.dto.response.ClassResponseDTO;
import org.schoolmanagement.schoolmanagement.entity.SchoolClass;
import org.schoolmanagement.schoolmanagement.repository.ClassRepository;
import org.schoolmanagement.schoolmanagement.service.ClassService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;

    @Override
    public Page<ClassResponseDTO> getAllClasses(Pageable pageable) {
        return classRepository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    private ClassResponseDTO mapToResponseDTO(SchoolClass schoolClass) {
        Integer academicYearId = null;
        String academicYearName = null;

        if (schoolClass.getAcademicYear() != null) {
            academicYearId = schoolClass.getAcademicYear().getId();
            academicYearName = schoolClass.getAcademicYear().getName();
        }

        return new ClassResponseDTO(
                schoolClass.getId(),
                schoolClass.getName(),
                schoolClass.getGradeLevel(),
                academicYearId,
                academicYearName,
                schoolClass.getCreatedAt()
        );
    }
}
