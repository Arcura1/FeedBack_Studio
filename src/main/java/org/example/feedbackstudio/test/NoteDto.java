package org.example.feedbackstudio.test;

import org.springframework.data.annotation.Id;


public class NoteDto {
    @Id
    private String id;
    private int Xcoordinate;
    private int Ycoordinate;
    private Long pdfId;
    private String note;

    public NoteDto() {

    }

    public NoteDto(String id, int Xcoordinate, int Ycoordinate) {
        this.id = id;
        this.Xcoordinate = Xcoordinate;
        this.Ycoordinate = Ycoordinate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getXcoordinate() {
        return Xcoordinate;
    }

    public void setXcoordinate(int Xcoordinate) {
        this.Xcoordinate = Xcoordinate;
    }

    public int getYcoordinate() {
        return Ycoordinate;
    }

    public void setYcoordinate(int Ycoordinate) {
        this.Ycoordinate = Ycoordinate;
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
