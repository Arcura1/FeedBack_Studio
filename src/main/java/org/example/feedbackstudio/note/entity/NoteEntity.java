package org.example.feedbackstudio.note.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Notes")
public class NoteEntity {
    @Id
    private String id;
    private Long xcoordinate;
    private Long ycoordinate;
    private Long pdfId;
    private String title;
    private Number page;

    private String note;
    @DBRef
    private User user;

    @DBRef
    private PdfInfoEntity PdfInfoEntity;

    public NoteEntity() {
    }

    public NoteEntity(Long xcoordinate, Long ycoordinate) {
        this.xcoordinate = xcoordinate;
    }


}
