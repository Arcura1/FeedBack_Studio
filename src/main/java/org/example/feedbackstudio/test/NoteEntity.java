package org.example.feedbackstudio.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class NoteEntity {
    @Id
    private String id;
    private int Xcoordinate;
    private int Ycoordinate;
    private String note;

    public NoteEntity() {


    }

    public NoteEntity(int Xcoordinate, int Ycoordinate) {
        this.Xcoordinate = Xcoordinate;
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
}
