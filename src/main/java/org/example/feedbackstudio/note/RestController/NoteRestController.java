package org.example.feedbackstudio.note.RestController;

import org.example.feedbackstudio.note.Model.NoteModel;
import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.pdfInfo.repository.PdfInfoRepository;
import org.example.feedbackstudio.note.service.NoteService;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class NoteRestController {

    private final NoteService noteService;
    private final String UPLOAD_DIR = "src/main/resources/static/";

    @Autowired
    org.example.feedbackstudio.MessageSender messageSender;

    @Autowired
    private PdfInfoRepository PdfInfoRepository;
    @Autowired
    private GenericResponseService responseBuilder;

    @Autowired
    public NoteRestController(NoteService noteService) {
        this.noteService = noteService;
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping("/delAll/{id}")
    public ResponseEntity<String> delAll(@PathVariable Long id) {
        noteService.delByPdfinfo(id);
        return new ResponseEntity<>("silindi", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/viewAll")
    public ResponseEntity<List<NoteEntity>> viewAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(noteService.viewAll(), headers, HttpStatus.OK);
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/viewAll/{id}")
    public ResponseEntity<List<NoteEntity>> viewAllById(@PathVariable Long id) {
        // HTTP başlıkları oluşturma
        HttpHeaders headers = new HttpHeaders();

        // NoteService'den veriyi getirme
        List<NoteEntity> result = noteService.viewByPdfInfo(id);

        // JSON içeriği belirleme
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        // Sonuç döndürme
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @GetMapping("/getByPdf")
    public ResponseEntity<List<NoteEntity>> findAll(@RequestParam Long pdfId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        // Service üzerinden veri al
        List<NoteEntity> notes = noteService.viewByPdfId(pdfId);

        // Eğer sonuç boşsa 404 döndür
        if (notes == null || notes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Başarılı bir şekilde sonuçları döndür
        return new ResponseEntity<>(notes, headers, HttpStatus.OK);
    }



    @GetMapping("/view")
    public NoteModel view(@RequestParam Long NoteId)
    {
        return noteService.view(NoteId);
    }

    @PutMapping("/add")
    public ResponseEntity<String> add(@RequestBody NoteQueryModel noteDto) {
        System.out.println(noteDto);
        messageSender.sendMessageNote(noteDto);
        return ResponseEntity.ok("done");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody NoteQueryModel noteDto) {
        return "oray";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        return noteService.deleteAll();
    }

    @PostMapping("/edit")
    public String edit() {
        return "oray";
    }


}
