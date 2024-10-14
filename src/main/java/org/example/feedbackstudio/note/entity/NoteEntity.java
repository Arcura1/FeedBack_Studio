package org.example.feedbackstudio.note.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class NoteEntity {
    @Id
    private String id;
    private Long xcoordinate;
    private Long ycoordinate;
    private Long pdfId;
    private String note;

    public NoteEntity() {
    }

    public NoteEntity(Long xcoordinate, Long ycoordinate) {
        this.xcoordinate = xcoordinate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getxcoordinate() {
        return xcoordinate;
    }

    public void setxcoordinate(Long xcoordinate) {
        this.xcoordinate = xcoordinate;
    }

    public Long getycoordinate() {
        return ycoordinate;
    }

    public void setycoordinate(Long ycoordinate) {
        this.ycoordinate = ycoordinate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public Long getPdfId() {
        return pdfId;
    }
    public void setPdfId(Long pdfId) {
        this.pdfId = pdfId;
    }
}
