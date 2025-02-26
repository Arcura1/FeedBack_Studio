package org.example.feedbackstudio.note.repository;

import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    Optional<NoteEntity> findById(Long Id);
    List<NoteEntity> findByTitle(String title);
    List<NoteEntity> findByTitleContaining(String title);
    List<NoteEntity> findByPdfInfoEntity(PdfInfoEntity pdfInfoEntity);

    List<NoteEntity> findByPdfInfoEntityId(Long pdfInfoEntityId);
}
