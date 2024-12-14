package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.note.Model.NoteModel;
import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.Model.PdfUploadQueryModel;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;

import java.util.List;

public interface PdfInfoService {
    String add(PdfUploadQueryModel queryModel);
    PdfInfoEntity findById(String Id);
}
