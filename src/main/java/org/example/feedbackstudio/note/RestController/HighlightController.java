package org.example.feedbackstudio.note.RestController;

import org.example.feedbackstudio.login.dao.UserRepository;
import org.example.feedbackstudio.login.entity.User;
import org.example.feedbackstudio.note.Model.HighlightQueryModel;
import org.example.feedbackstudio.note.entity.HighlightEntity;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.example.feedbackstudio.note.repository.PdfInfoRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PdfInfoRepository pdfInfoRepository;

    // Yeni highlight kaydetme endpoint (POST)
    @PostMapping
    public ResponseEntity<HighlightEntity> saveHighlight(@RequestBody HighlightQueryModel highlightQueryModel) {
        // Parametre kontrolü ve validation eklenebilir
        if (highlightQueryModel.getStartX() == 0 || highlightQueryModel.getStartY() == 0) {
            // Return a bad request response with a custom error message
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }

        // Find the user by ID. If not found, return a 404 Not Found error.
        User user = userRepository.findById(highlightQueryModel.getUserId()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }

        // Find the PdfInfoEntity by ID. If not found, return a 404 Not Found error.
        PdfInfoEntity pdfInfo = pdfInfoRepository.findById(highlightQueryModel.getPdfId());
        if (pdfInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }

        // Create the HighlightEntity object
        HighlightEntity highlight = new HighlightEntity();
        highlight.setStartX(highlightQueryModel.getStartX());
        highlight.setStartY(highlightQueryModel.getStartY());
        highlight.setEndX(highlightQueryModel.getEndX());
        highlight.setEndY(highlightQueryModel.getEndY());
        highlight.setCurrentPage(highlightQueryModel.getCurrentPage());
        highlight.setPdfInfo(pdfInfo);
        highlight.setUser(user);

        // Save the highlight and return a response with the created entity
        HighlightEntity  savedHighlight = highlightService.saveHighlight(highlight);

        // Return the saved highlight entity with a 201 Created status
        return new ResponseEntity<>(savedHighlight, HttpStatus.CREATED); // 201 Created
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/viewH/{id}")
    public ResponseEntity<List<HighlightEntity>> getBypdfId(@PathVariable String id) {
    highlightService.getAllHighlightsByPdfId(id);
    return new ResponseEntity<>(highlightService.getAllHighlightsByPdfId(id), HttpStatus.OK);
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
