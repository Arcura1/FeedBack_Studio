package org.example.feedbackstudio.note.repository;

import org.example.feedbackstudio.note.entity.NoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<NoteEntity, Integer> {
    NoteEntity findById(String Id);

    @Query("{ 'PdfInfoEntity.id': ?0 }")
    List<NoteEntity> findByPdfInfoEntityId(String pdfInfoEntityId);
}
