package org.example.feedbackstudio.note.RestController;

import org.example.feedbackstudio.note.Model.NoteModel;
import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

        @RestController
@CrossOrigin(origins = "*")
public class NoteRestController {

    private final NoteService noteService;
    private final String UPLOAD_DIR = "src/main/resources/static/";


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

    @GetMapping("/view")
    public NoteModel  view() {
        return noteService.view();
    }

    @PutMapping("/add")
    public String add(@RequestBody NoteQueryModel noteDto) {

        return this.noteService.add(noteDto);
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
@CrossOrigin(origins = "*")
    @GetMapping("/pdf")
    public ResponseEntity<Resource> getPdf() {
        File pdfFile = new File("src/main/resources/static/example.pdf");
        Resource resource = new FileSystemResource(pdfFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + pdfFile.getName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
    @PostMapping("/uploadPdf")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Dosya boş!", HttpStatus.BAD_REQUEST);
        }

        // PDF dosyasının ismini alın
        String fileName = file.getOriginalFilename();
        // Dosyanın kaydedileceği tam yol
        File destinationFile = new File(UPLOAD_DIR + fileName);

        try (FileOutputStream outputStream = new FileOutputStream(destinationFile)) {
            // PDF dosyasını OutputStream'e yazın
            outputStream.write(file.getBytes());
            return new ResponseEntity<>("Dosya başarıyla yüklendi: " + fileName, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Dosya yüklenirken bir hata oluştu: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/download-pdf")
    public ResponseEntity<Resource> downloadPdf() {
        // PDF dosyasının yolu
        File file = new File("src/main/resources/static/example.pdf");
        Resource resource = new FileSystemResource(file);

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
