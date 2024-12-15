package org.example.feedbackstudio.note.repository;

import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PdfInfoRepository extends CrudRepository<PdfInfoEntity,Integer> {
    public PdfInfoEntity findById(String Id);
    PdfInfoEntity findByTitle(String title);
    List<PdfInfoEntity> findByhomeworkEntity_id(String homeworkEntity_id);
    PdfInfoEntity findByHomeworkEntityIdAndUserId(String homeworkId, String userId);
}
