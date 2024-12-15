package org.example.feedbackstudio.note.RestController;

import org.example.feedbackstudio.note.entity.HighlightEntity;
import org.example.feedbackstudio.note.service.HighlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/highlights")
@CrossOrigin(origins = "*")
public class HighlightController {

    @Autowired
    private HighlightService highlightService;

    // Yeni highlight kaydetme endpoint (POST)
    @PostMapping
    public ResponseEntity<HighlightEntity> saveHighlight(@RequestBody HighlightEntity highlightEntity) {
        // Parametre kontrolü ve validation eklenebilir
        if (highlightEntity.getStartX() == 0 || highlightEntity.getStartY() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }

        // Highlight verisini kaydet
        HighlightEntity savedHighlight = highlightService.saveHighlight(highlightEntity);

        // Başarıyla kaydedildiği için 201 Created yanıtı döndürüyoruz
        return new ResponseEntity<>(savedHighlight, HttpStatus.CREATED); // 201 Created
    }


    // Belirli bir startX ve startY'ye göre highlight'ları getiren endpoint (GET)
    @GetMapping("/coordinates")
    public ResponseEntity<List<HighlightEntity>> getHighlightsByCoordinates(
            @RequestParam int startX,
            @RequestParam int startY) {

        List<HighlightEntity> highlights = highlightService.getHighlightsByCoordinates(startX, startY);

        // Eğer sonuç yoksa 204 No Content dönüyoruz
        if (highlights.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        }

        // Sonuç varsa 200 OK ile listeleri gönderiyoruz
        return new ResponseEntity<>(highlights, HttpStatus.OK); // 200 OK
    }
}
