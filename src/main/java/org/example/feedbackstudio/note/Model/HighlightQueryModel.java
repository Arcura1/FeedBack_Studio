package org.example.feedbackstudio.note.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HighlightQueryModel {
    private int startX;
    private int endX;
    private int startY;
    private int endY;
    private String pdfId;
    private String userId;
    private int currentPage;
}
