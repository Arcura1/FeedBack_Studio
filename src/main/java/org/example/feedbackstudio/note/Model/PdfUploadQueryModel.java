package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.entity.User;
import org.example.feedbackstudio.note.entity.HomeworkEntity;

@Getter
@Setter
public class PdfUploadQueryModel {
    private String HomeworkId;
    private String UserId;

    public PdfUploadQueryModel() {

    }
    public PdfUploadQueryModel(String HomeworkId, String UserId) {
        this.HomeworkId = HomeworkId;
        this.UserId = UserId;
    }
}
