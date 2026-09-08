package org.schoolmanagement.schoolmanagement.repository;

import org.schoolmanagement.schoolmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    @Query("SELECT MAX(u.userIdNumber) FROM User u WHERE u.userIdNumber LIKE CONCAT(:prefix,'%')")
    Optional<String> findMaxUserIdNumberByPrefix(@Param("prefix") String prefix);

    Optional<User> findByEmail(String email);
}
