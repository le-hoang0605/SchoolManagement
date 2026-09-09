package org.schoolmanagement.schoolmanagement.service;

import org.schoolmanagement.schoolmanagement.dto.response.StudentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SectionService {
    Page<StudentResponseDTO> getStudentsBySectionId(Integer sectionId, Pageable pageable);
}
