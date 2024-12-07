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

    @GetMapping("/view")
    public NoteModel view(@RequestParam String NoteId)
    {
        return noteService.view(NoteId);
    }

    @PutMapping("/add")
    public String add(@RequestBody NoteQueryModel noteDto) {
        noteDto.setPdfId(null);
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
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file, @RequestParam PdfUploadQueryModel pdfUploadQueryModel) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Dosya boş!", HttpStatus.BAD_REQUEST);
        }
        PdfInfoEntity newPdf = new PdfInfoEntity();
        newPdf.setTitle(file.getOriginalFilename());


        // PDF dosyasının ismini alın
        String fileName = file.getOriginalFilename();
        // Dosyanın kaydedileceği tam yol

        PdfInfoRepository.save(newPdf);
        File destinationFile = new File(UPLOAD_DIR + newPdf.getId()+fileName);

        try (FileOutputStream outputStream = new FileOutputStream(destinationFile)) {
            // PDF dosyasını OutputStream'e yazın
            outputStream.write(file.getBytes());
            return new ResponseEntity<>("Dosya başarıyla yüklendi: " + fileName, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Dosya yüklenirken bir hata oluştu: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/download-pdf")
//    public ResponseEntity<Resource> downloadPdf() {
//        // PDF dosyasının yolu
//        File file = new File("src/main/resources/static/sample1.pdf");
//        Resource resource = new FileSystemResource(file);
//
//        if (!resource.exists()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(file.length())
//                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
//                .body(resource);
//    }
}
