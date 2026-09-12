package org.schoolmanagement.schoolmanagement.repository;

import org.schoolmanagement.schoolmanagement.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
