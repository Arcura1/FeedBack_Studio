package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.entity.User;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;

@Getter
@Setter
public class NoteQueryModel {

    private String id;
    private Long xcoordinate;
    private Long ycoordinate;
    private Long pdfId;
    private String note;
    private Long page;

    private String user;

    private String pdfInfoEntity;

    public NoteQueryModel() {

    }

    public NoteQueryModel(String id, Long xcoordinate, Long ycoordinate) {
        this.id = id;
        this.xcoordinate = xcoordinate;
        this.ycoordinate = ycoordinate;
    }

}
