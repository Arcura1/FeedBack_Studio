package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.note.entity.HighlightEntity;
import org.example.feedbackstudio.note.repository.HighlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


public interface HighlightService {
    List<HighlightEntity> getAllHighlights();
    List<HighlightEntity> getAllHighlightsByPdfId(String id);
    List<HighlightEntity> getHighlightsByCoordinates(int startX, int startY);
    HighlightEntity saveHighlight(HighlightEntity highlightEntity);
}
