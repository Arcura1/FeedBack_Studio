package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.note.entity.HighlightEntity;
import org.example.feedbackstudio.note.repository.HighlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HighlightService {

    @Autowired
    private HighlightRepository highlightRepository;

    // Tüm highlight'ları getiren metod
    public List<HighlightEntity> getAllHighlights() {
        return highlightRepository.findAll();
    }

    // Belirli bir startX ve startY'ye göre highlight'ları getiren metod
    public List<HighlightEntity> getHighlightsByCoordinates(int startX, int startY) {
        return highlightRepository.findByStartXAndStartY(startX, startY);
    }

    // Highlight kaydetme metod
    public HighlightEntity saveHighlight(HighlightEntity highlightEntity) {
        return highlightRepository.save(highlightEntity);
    }
}
