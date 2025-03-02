package org.example.feedbackstudio.login.authority.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Yetki adı (örn: ROLE_ADMIN, ROLE_USER)

    @Column(length = 255)
    private String description; // Yetkinin açıklaması (isteğe bağlı)
}
