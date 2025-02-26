package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.entity.User;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;

@Getter
@Setter
public class NoteQueryModel {

    private Long id;
    private Long xcoordinate;
    private Long ycoordinate;
    private String title;
    private String note;
    private Long page;

    private Long user;

    private Long pdfInfoEntity;

    public NoteQueryModel() {

    }

    public NoteQueryModel(Long id, Long xcoordinate, Long ycoordinate) {
        this.id = id;
        this.xcoordinate = xcoordinate;
        this.ycoordinate = ycoordinate;
    }

}
