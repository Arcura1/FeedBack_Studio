package org.example.feedbackstudio.classroom.entitiy;

import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.organization.entity.organizationEntity;

@Entity
@Table(name = "classroomUser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomUserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Organization ID doğrudan tutulacak
    @Column(name = "classroom_id", insertable = true, updatable = true)
    private Long classroomId;

    // Organization entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_id", insertable = false, updatable = false)
    private classroomEntity classroom;


    // Organization ID doğrudan tutulacak

    @Column(name = "user_id", insertable = true, updatable = true)
    private Long userId;

    // Organization entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
