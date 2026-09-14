package org.schoolmanagement.schoolmanagement.repository;

import org.schoolmanagement.schoolmanagement.entity.Program;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.DayOfWeek;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;

public interface ProgramRepository extends JpaRepository<Program, Integer> {
    @EntityGraph(attributePaths = {"section", "course", "teacher", "academicYear"})
    Page<Program> findBySectionId(Integer sectionId, Pageable pageable);

    @EntityGraph(attributePaths = {"section", "course", "teacher", "academicYear"})
    Page<Program> findByTeacherId(Integer teacherId, Pageable pageable);

    @Query("""
                SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
                FROM Program p
                WHERE p.teacher.id = :teacherId
                  AND p.dayOfWeek = :dayOfWeek
                  AND (:academicYearId IS NULL OR p.academicYear.id = :academicYearId)
                  AND p.startTime < :endTime
                  AND p.endTime > :startTime
            """)
    boolean existsTeacherScheduleConflict(
            @Param("teacherId") Integer teacherId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("academicYearId") Integer academicYearId,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);

    @Query("""
                SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
                FROM Program p
                WHERE p.room = :room
                  AND p.dayOfWeek = :dayOfWeek
                  AND (:academicYearId IS NULL OR p.academicYear.id = :academicYearId)
                  AND p.startTime < :endTime
                  AND p.endTime > :startTime
            """)
    boolean existsRoomScheduleConflict(
            @Param("room") String room,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("academicYearId") Integer academicYearId,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}
