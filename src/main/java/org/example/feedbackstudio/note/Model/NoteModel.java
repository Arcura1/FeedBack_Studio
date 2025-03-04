package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.note.pdfInfo.entitiy.PdfInfoEntity;


@Getter
@Setter
public class NoteModel {

    private Long id;
    private Long xcoordinate;
    private Long ycoordinate;
    private String title;
    private Long page;
    private String note;
    private User user;
    private PdfInfoEntity PdfInfoEntity;
}
