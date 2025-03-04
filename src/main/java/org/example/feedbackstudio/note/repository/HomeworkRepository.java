package org.example.feedbackstudio.note.repository;

import org.example.feedbackstudio.note.entity.HomeworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<HomeworkEntity, Long> {
    Optional<HomeworkEntity> findById(Long id); // "Id" yerine "id" yazıldı.
}
