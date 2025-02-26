package org.example.feedbackstudio.note.repository;

import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PdfInfoRepository extends JpaRepository<PdfInfoEntity,Long> {
    public Optional<PdfInfoEntity> findById(Long Id);
    PdfInfoEntity findByTitle(String title);
    List<PdfInfoEntity> findByhomeworkEntity_id(Long homeworkEntity_id);
    PdfInfoEntity findByHomeworkEntityIdAndUserId(Long homeworkId, Long userId);
}
