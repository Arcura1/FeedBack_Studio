package org.example.feedbackstudio.login.role.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.login.authority.entity.Authority;
import org.example.feedbackstudio.organization.entity.organizationEntity;

@Entity
@Table(name = "roleAuthority")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class roleAuthorityEntitiy {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // role ID doğrudan tutulacak
    @Column(name = "role_id", insertable = true, updatable = false)
    private Long roleId;

    // role entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private roleEntity role;

    // authority ID doğrudan tutulacak
    @Column(name = "authority_id", insertable = true, updatable = false)
    private Long authorityId;

    // authority entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority_id", insertable = false, updatable = false)
    private Authority authority;
}
