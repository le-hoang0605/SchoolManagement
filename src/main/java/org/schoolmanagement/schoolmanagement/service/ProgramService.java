package org.schoolmanagement.schoolmanagement.service;

import org.schoolmanagement.schoolmanagement.dto.request.ProgramRequestDTO;
import org.schoolmanagement.schoolmanagement.dto.response.ProgramResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProgramService {
    Page<ProgramResponseDTO> getProgramsBySectionId(Integer sectionId, Pageable pageable);

    Page<ProgramResponseDTO> getProgramsByTeacherId(Integer teacherId, Pageable pageable);

    ProgramResponseDTO createProgram(ProgramRequestDTO dto);
}
