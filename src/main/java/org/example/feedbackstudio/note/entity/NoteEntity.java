package org.example.feedbackstudio.note.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.note.pdfInfo.entitiy.PdfInfoEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notes")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x_coordinate", nullable = false)
    private Long xcoordinate;

    @Column(name = "y_coordinate", nullable = false)
    private Long ycoordinate;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "page", nullable = false)
    private Long page;

    @Column(name = "note", columnDefinition = "TEXT", nullable = false)
    private String note;

    @Column(name = "user_id",  insertable = true, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",  insertable = false, updatable = false)
    private User user;

    @Column(name = "pdf_info_id",  insertable = true, updatable = false)
    private Long pdfInfoEntityId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pdf_info_id",  insertable = false, updatable = false)
    private PdfInfoEntity pdfInfoEntity;

    public NoteEntity(Long xcoordinate, Long ycoordinate) {
        this.xcoordinate = xcoordinate;
    }
}
