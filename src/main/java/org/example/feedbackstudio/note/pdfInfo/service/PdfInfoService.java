package org.example.feedbackstudio.note.pdfInfo.service;

import org.example.feedbackstudio.note.Model.MixQueryModel;
import org.example.feedbackstudio.note.Model.PdfUploadQueryModel;
import org.example.feedbackstudio.note.pdfInfo.entitiy.PdfInfoEntity;

import java.util.List;

public interface PdfInfoService {
    Long add(PdfUploadQueryModel queryModel);
    PdfInfoEntity findById(Long Id);
    PdfInfoEntity findAllByHU(MixQueryModel queryModel);
    List<PdfInfoEntity> findByHomevork(Long homevork);
    // Convert PdfInfoEntity to MixQueryModel
    MixQueryModel convertToMixQueryModel(PdfInfoEntity pdfInfoEntity); // Yeni metodu ekliyoruz
}
