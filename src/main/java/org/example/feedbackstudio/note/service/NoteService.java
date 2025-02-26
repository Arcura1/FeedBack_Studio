package org.example.feedbackstudio.note.service;


import org.example.feedbackstudio.note.Model.NoteModel;
import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.entity.NoteEntity;

import java.util.List;

public interface NoteService {
    public NoteModel view(String NoteId);
    public List<NoteEntity> viewAll();
    public String deleteAll();
    public String add(NoteQueryModel note);
    public List<NoteEntity> viewByPdfId(String pdf);
    public List<NoteEntity> viewByPdfInfo(String id);
    public String delByPdfinfo(String id);
}
