package org.example.feedbackstudio.note.repository;

import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.springframework.data.repository.CrudRepository;

public interface PdfInfoRepository extends CrudRepository<PdfInfoEntity,Integer> {
    public PdfInfoEntity findById(String Id);
    PdfInfoEntity findByHomeworkEntityIdAndUserId(String homeworkId, String userId);
}
