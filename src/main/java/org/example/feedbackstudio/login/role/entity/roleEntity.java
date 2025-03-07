package org.example.feedbackstudio.login.role.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.login.authority.authorityenum.AuthorityType;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class roleEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 255)
    private String description; // Yetkinin açıklaması (isteğe bağlı)

    @Enumerated(EnumType.STRING)  // Enum'ı String olarak saklar (örn: "ADD_HOMEWORK")
    @Column(nullable = false)
    private RoleTypeEnum roleTypeEnum;


}