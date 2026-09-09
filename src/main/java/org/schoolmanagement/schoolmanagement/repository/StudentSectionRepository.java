package org.schoolmanagement.schoolmanagement.repository;

import org.schoolmanagement.schoolmanagement.entity.StudentSection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentSectionRepository extends JpaRepository<StudentSection, Integer> {
    Page<StudentSection> findBySectionId(Integer sectionId, Pageable pageable);
}
