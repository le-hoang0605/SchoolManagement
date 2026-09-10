package org.schoolmanagement.schoolmanagement.repository;

import org.schoolmanagement.schoolmanagement.entity.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Integer> {
    boolean existsByCourseIdAndSectionIdAndAcademicYearId(Integer courseId, Integer sectionId, Integer academicYearId);
}
