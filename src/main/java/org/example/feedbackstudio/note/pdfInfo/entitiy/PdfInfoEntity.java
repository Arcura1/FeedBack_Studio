package org.example.feedbackstudio.note.pdfInfo.entitiy;

import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.note.entity.HomeworkEntity;

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

    @JoinColumn(name = "homework_id",  insertable = true, updatable = false)
    private Long homeworkEntityId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "homework_id", insertable = false, updatable = false)
    private HomeworkEntity homeworkEntity;


    @Column(name = "user_id",  insertable = true, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
