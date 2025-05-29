package org.example.feedbackstudio.note.pdfInfo.repository;

import org.example.feedbackstudio.note.pdfInfo.entitiy.PdfInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PdfInfoRepository extends JpaRepository<PdfInfoEntity,Long> {
    public Optional<PdfInfoEntity> findById(Long id);
    PdfInfoEntity findByTitle(String title);
    List<PdfInfoEntity> findByhomeworkEntity_id(Long homeworkEntity_id);
    PdfInfoEntity findByHomeworkEntityIdAndUserId(Long homeworkId, Long userId);
    List<PdfInfoEntity> findAllByhomeworkEntity_id(Long homeworkId);
}
