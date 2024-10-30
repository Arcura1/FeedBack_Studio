package org.example.feedbackstudio.note.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "PdfInfo")
public class PdfInfoEntity {
    @Id
    private String id;
    private String title;
    private String content;
    private String Url;
    private Number Xsize;
    private Number Ysize;
    private Number PageSize;

    @DBRef
    private HomeworkEntity  homeworkEntity;

    public PdfInfoEntity() {

    }
}
