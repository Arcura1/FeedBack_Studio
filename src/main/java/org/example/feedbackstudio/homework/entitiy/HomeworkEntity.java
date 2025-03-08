package org.example.feedbackstudio.homework.entitiy;

import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.organization.entity.organizationEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "homework")
public class HomeworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // UUID için AUTO kullanılıyor
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "teacher_id", insertable = true, updatable = false)
    private Long teacherId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private User teacher;

    @Column(name = "classroom_id", insertable = true, updatable = false)
    private Long classroomId;

    // Organization entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_id", insertable = false, updatable = false)
    private classroomEntity classroom;

}
