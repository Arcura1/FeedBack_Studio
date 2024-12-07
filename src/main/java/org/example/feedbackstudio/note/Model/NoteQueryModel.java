package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteQueryModel {

    private String id;
    private Long xcoordinate;
    private Long ycoordinate;
    private Long pdfId;
    private String explanation;
    private String note;

    public NoteQueryModel() {

    }

    public NoteQueryModel(String id, Long xcoordinate, Long ycoordinate) {
        this.id = id;
        this.xcoordinate = xcoordinate;
        this.ycoordinate = ycoordinate;
    }

}
