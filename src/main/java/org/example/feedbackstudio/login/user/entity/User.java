package org.example.feedbackstudio.login.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.login.role.entity.roleEntity;

@Entity
@Table(name = "users") // PostgreSQL'de tablo adı küçük harf olmalı
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PostgreSQL'de kimlik sütunu için uygun veri tipi

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    // role ID doğrudan tutulacak
    @Column(name = "role_id", insertable = true, updatable = false)
    private Long roleId;

    // role entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private roleEntity roleEntity;

    public User(String firstName, String lastName, String email, String phone, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role != null ? role : "student";
    }
}
