package org.schoolmanagement.schoolmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Gender;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "user_id_number", nullable = false, length = 20, unique = true)
    private String userIdNumber;

    @Column(name = "phone", length = 30)
    private String phone = null;

    @Column(name = "avatar")
    private String avatar = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender = Gender.male;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth = null;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address = null;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "must_change_password")
    private Boolean mustChangePassword = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
