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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdf_info_id", nullable = false)
    private PdfInfoEntity pdfInfoEntity;

    public NoteEntity(Long xcoordinate, Long ycoordinate) {
        this.xcoordinate = xcoordinate;
    }
}
