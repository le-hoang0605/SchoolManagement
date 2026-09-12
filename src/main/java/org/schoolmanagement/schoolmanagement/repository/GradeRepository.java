package org.schoolmanagement.schoolmanagement.repository;

import org.schoolmanagement.schoolmanagement.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    @EntityGraph(attributePaths = {"student", "course", "section", "recordedBy"})
    Page<Grade> findByStudentId(Integer studentId, Pageable pageable);

    @EntityGraph(attributePaths = {"student", "course", "section", "recordedBy"})
    Page<Grade> findBySectionIdAndCourseId(Integer sectionId, Integer courseId, Pageable pageable);
}
