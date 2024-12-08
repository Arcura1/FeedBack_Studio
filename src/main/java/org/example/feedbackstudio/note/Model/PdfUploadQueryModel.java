package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.entity.User;
import org.example.feedbackstudio.note.entity.HomeworkEntity;

@Getter
@Setter
public class PdfUploadQueryModel {

    private String title;
    private String content;
    private Number xsize;
    private Number ysize;
    private Number pageSize;

    private String homeworkId;
    private String userId;

    public PdfUploadQueryModel() {

    }
    public PdfUploadQueryModel(String HomeworkId, String UserId) {
        this.homeworkId = HomeworkId;
        this.userId = UserId;
    }
}
