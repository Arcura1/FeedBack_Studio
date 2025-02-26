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
@Table(name = "pdf_info")
public class PdfInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // UUID için AUTO kullanılıyor
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "x_size", nullable = false)
    private Integer xSize;

    @Column(name = "y_size", nullable = false)
    private Integer ySize;

    @Column(name = "page_size", nullable = false)
    private Integer pageSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_id", nullable = false)
    private HomeworkEntity homeworkEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
