package org.example.feedbackstudio.note.RestController;

import org.example.feedbackstudio.MessageSender;
import org.example.feedbackstudio.note.Model.NoteModel;
import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.Model.PdfUploadQueryModel;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.example.feedbackstudio.note.repository.NoteRepository;
import org.example.feedbackstudio.note.repository.PdfInfoRepository;
import org.example.feedbackstudio.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public NoteRestController(NoteService noteService) {
        this.noteService = noteService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/viewAll")
    public ResponseEntity<List<NoteEntity>> viewAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(noteService.viewAll(), headers, HttpStatus.OK);
    }

    @GetMapping("/getByPdf")
    public ResponseEntity<List<NoteEntity>> findAll(@RequestParam String pdfId) {
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
    public NoteModel view(@RequestParam String NoteId)
    {
        return noteService.view(NoteId);
    }

    @PutMapping("/add")
    public String add(@RequestBody NoteQueryModel noteDto) {
        System.out.println(noteDto);
        messageSender.sendMessageNote(noteDto);
        return "done";
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
