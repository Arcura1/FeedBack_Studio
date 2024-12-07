package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.entity.User;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;


@Getter
@Setter
public class NoteModel {

    private String id;
    private Long xcoordinate;
    private Long ycoordinate;
    private String title;
    private Number page;
    private String note;
    private User user;
    private PdfInfoEntity PdfInfoEntity;
}
