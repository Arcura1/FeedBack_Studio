package org.example.feedbackstudio.note.repository;

import org.example.feedbackstudio.note.entity.NoteEntity;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<NoteEntity, Integer> {
    NoteEntity findById(String Id);
}
