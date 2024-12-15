package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.note.Model.MixQueryModel;
import org.example.feedbackstudio.note.Model.PdfUploadQueryModel;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;

import java.util.List;

public interface PdfInfoService {
    String add(PdfUploadQueryModel queryModel);
    PdfInfoEntity findById(String Id);
    PdfInfoEntity findAllByHU(MixQueryModel queryModel);
    List<PdfInfoEntity> findByHomevork(String homevork);
    // Convert PdfInfoEntity to MixQueryModel
    MixQueryModel convertToMixQueryModel(PdfInfoEntity pdfInfoEntity); // Yeni metodu ekliyoruz
}
