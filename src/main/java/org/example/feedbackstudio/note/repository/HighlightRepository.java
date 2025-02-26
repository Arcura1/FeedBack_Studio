package org.example.feedbackstudio.note.repository;

import org.example.feedbackstudio.note.entity.HighlightEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface HighlightRepository extends MongoRepository<HighlightEntity, String> {
    // Bu metot, belirli bir startX ve startY'ye göre highlight'ları alır
    List<HighlightEntity> findByStartXAndStartY(int startX, int startY);

    // Diğer özelleştirilmiş sorgu metotlarını buraya ekleyebilirsiniz
}
