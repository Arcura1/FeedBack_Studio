package org.example.feedbackstudio.note.converter;

import org.example.feedbackstudio.note.Model.NoteModel;
import org.example.feedbackstudio.note.entity.NoteEntity;

public class NoteConverter {

    // NoteEntity'den NoteModel'e dönüşüm
    public static NoteModel convertToModel(NoteEntity entity) {
        if (entity == null) {
            return null;
        }

        NoteModel model = new NoteModel();
        model.setId(entity.getId());
        model.setXcoordinate(entity.getXcoordinate());
        model.setYcoordinate(entity.getYcoordinate());
        model.setTitle(entity.getTitle());
        model.setPage(entity.getPage());
        model.setNote(entity.getNote());
        model.setUser(entity.getUser());  // User'ın DBRef olması nedeniyle sadece id'yi almak yeterli olabilir.
        model.setPdfInfoEntity(entity.getPdfInfoEntity()); // PdfInfoEntity'yi de set ediyoruz

        return model;
    }

    // NoteModel'den NoteEntity'ye dönüşüm
    public static NoteEntity convertToEntity(NoteModel model) {
        if (model == null) {
            return null;
        }

        NoteEntity entity = new NoteEntity();
        entity.setId(model.getId());
        entity.setXcoordinate(model.getXcoordinate());
        entity.setYcoordinate(model.getYcoordinate());
        entity.setTitle(model.getTitle());
        entity.setPage(model.getPage());
        entity.setNote(model.getNote());
        entity.setUser(model.getUser());  // User nesnesini DBRef olarak set ediyoruz
        entity.setPdfInfoEntity(model.getPdfInfoEntity());  // PdfInfoEntity'yi de set ediyoruz

        return entity;
    }
}
