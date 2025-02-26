package org.example.feedbackstudio.note.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter

@Document(collection = "highlights")
public class HighlightEntity {

    @Id
    private String id;

    private int startX;
    private int endX;
    private int startY;
    private int endY;
    private User user;
    private PdfInfoEntity pdfInfo;
    private int currentPage;

    // Constructors
    public HighlightEntity() {
    }

    public HighlightEntity(int startX, int endX, int startY, int endY) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
    }

    @Override
    public String toString() {
        return "HighlightEntity{" +
                "id='" + id + '\'' +
                ", startX=" + startX +
                ", endX=" + endX +
                ", startY=" + startY +
                ", endY=" + endY +
                '}';
    }
}