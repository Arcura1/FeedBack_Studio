package org.example.feedbackstudio.note.Model;


public class NoteQueryModel {

    private String id;
    private Long xcoordinate;
    private Long ycoordinate;
    private Long pdfId;
    private String note;

    public NoteQueryModel() {

    }

    public NoteQueryModel(String id, Long xcoordinate, Long ycoordinate) {
        this.id = id;
        this.xcoordinate = xcoordinate;
        this.ycoordinate = ycoordinate;
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
