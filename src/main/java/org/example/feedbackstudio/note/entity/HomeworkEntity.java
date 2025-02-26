package org.example.feedbackstudio.note.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.login.entity.User;
import java.util.UUID;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;
}
