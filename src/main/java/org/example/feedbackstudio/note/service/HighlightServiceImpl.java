package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.note.entity.HighlightEntity;
import org.example.feedbackstudio.note.repository.HighlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HighlightServiceImpl implements HighlightService {
    @Autowired
    private HighlightRepository highlightRepository;

    // Tüm highlight'ları getiren metod
    public List<HighlightEntity> getAllHighlights() {
        return highlightRepository.findAll();
    }

    @Override
    public List<HighlightEntity> getAllHighlightsByPdfId(String id) {
        List<HighlightEntity> temp=highlightRepository.findAll();
        List<HighlightEntity> result=new ArrayList<HighlightEntity>();
        temp.forEach(val->{
            if(val.getPdfInfo().getId().equals(id)){
                result.add(val);
            }
        });
        return result;
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
