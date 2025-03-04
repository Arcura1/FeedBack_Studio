package org.example.feedbackstudio.note.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.note.pdfInfo.entitiy.PdfInfoEntity;

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


    @Column(name = "user_id", insertable = true, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",  insertable = false, updatable = false)
    private User user;

    @Column(name = "pdf_info_id", insertable = true, updatable = false)
    private Long pdfInfoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pdf_info_id",  insertable = false, updatable = false)
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
