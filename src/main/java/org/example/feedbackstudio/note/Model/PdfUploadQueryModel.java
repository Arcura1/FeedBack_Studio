package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfUploadQueryModel {

    private String title;
    private String content;
    private Integer xsize;
    private Integer ysize;
    private Integer pageSize;

    private Long homeworkId;
    private Long userId;

    public PdfUploadQueryModel() {

    }
    public PdfUploadQueryModel(Long HomeworkId, Long UserId) {
        this.homeworkId = HomeworkId;
        this.userId = UserId;
    }
}
