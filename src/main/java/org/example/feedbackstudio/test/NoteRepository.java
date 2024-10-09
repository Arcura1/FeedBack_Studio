package org.example.feedbackstudio.test;

import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<NoteEntity, Integer> {
}
