package org.example.feedbackstudio.note.repository;

import org.example.feedbackstudio.note.entity.HighlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HighlightRepository extends JpaRepository<HighlightEntity, Long> {
    // Bu metot, belirli bir startX ve startY'ye göre highlight'ları alır
    List<HighlightEntity> findByStartXAndStartY(int startX, int startY);

    // Diğer özelleştirilmiş sorgu metotlarını buraya ekleyebilirsiniz
}
