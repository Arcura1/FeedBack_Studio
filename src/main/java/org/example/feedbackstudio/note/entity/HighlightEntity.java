package org.example.feedbackstudio.note.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.login.entity.User;

@Entity
@Table(name = "highlights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HighlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL için uygun kimlik üretme
    private Long id;

    @Column(name = "start_x", nullable = false)
    private int startX;

    @Column(name = "end_x", nullable = false)
    private int endX;

    @Column(name = "start_y", nullable = false)
    private int startY;

    @Column(name = "end_y", nullable = false)
    private int endY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdf_info_id", nullable = false)
    private PdfInfoEntity pdfInfo;

    @Column(name = "current_page", nullable = false)
    private int currentPage;

    @Override
    public String toString() {
        return "HighlightEntity{" +
                "id=" + id +
                ", startX=" + startX +
                ", endX=" + endX +
                ", startY=" + startY +
                ", endY=" + endY +
                ", currentPage=" + currentPage +
                '}';
    }
}
