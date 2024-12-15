package org.example.feedbackstudio.note.RestController;

import org.example.feedbackstudio.note.Model.PdfInfoModel;
import org.example.feedbackstudio.note.Model.PdfShowQueryModel;
import org.example.feedbackstudio.note.Model.PdfUploadQueryModel;
import org.example.feedbackstudio.note.entity.HomeworkEntity;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.example.feedbackstudio.note.repository.HomeworkRepository;
import org.example.feedbackstudio.note.repository.PdfInfoRepository;
import org.example.feedbackstudio.note.service.HomeworkService;
import org.example.feedbackstudio.note.service.NoteService;
import org.example.feedbackstudio.note.service.PdfInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.feedbackstudio.note.Model.MixQueryModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/pdf")
@CrossOrigin(origins = "*")
public class PdfInfoController {

    private final NoteService noteService;
    private final String UPLOAD_DIR = "src/main/resources/static/";

    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    org.example.feedbackstudio.MessageSender messageSender;

    @Autowired
    private org.example.feedbackstudio.note.repository.PdfInfoRepository PdfInfoRepository;

    @Autowired
    private PdfInfoService pdfInfoService;

    @Autowired
    public PdfInfoController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/mixQueryModel/{id}")
    public MixQueryModel getMixQueryModel(@PathVariable String id) {
        PdfInfoEntity pdfInfoEntity = pdfInfoService.findById(id);
        return pdfInfoService.convertToMixQueryModel(pdfInfoEntity);
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/pdf")
    public ResponseEntity<Resource> getPdf() {
        File pdfFile = new File(UPLOAD_DIR+"example.pdf");
        Resource resource = new FileSystemResource(pdfFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + pdfFile.getName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }


    // Cross-Origin ile farklı domain'lerden istek kabul et
    @CrossOrigin(origins = "*")
    @PostMapping("/findAllByHU")
    public ResponseEntity<PdfInfoEntity> getPdfById(@RequestBody MixQueryModel modela) {
        // Service metodunu çağırarak, mixQueryModel'i kullanabilirsiniz
        MixQueryModel model = new MixQueryModel();
        model.setHomeworkId(modela.getHomeworkId());  // örnek olarak, model'in 'homeworkId' alanını set ediyorum
        model.setUserId(modela.getUserId());  // model'in 'userId' alanını set ediyorum

        // PdfInfoService'yi çağırarak veriyi alıyoruz
        PdfInfoEntity pdfInfoEntity = pdfInfoService.findAllByHU(model);

        if (pdfInfoEntity != null) {
            return new ResponseEntity<>(pdfInfoEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pdfById")
    public ResponseEntity<Resource> getPdfById(@RequestBody PdfShowQueryModel querymodel) {
        // Dosya yolu oluşturuluyor
        File pdfFile = new File(UPLOAD_DIR + querymodel.getHomeworkId() + "/" + querymodel.getPdfInfoId() + ".pdf");

        // Dosyanın var olup olmadığını kontrol et
        if (!pdfFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Dosya bulunamazsa 404 dönülür
        }

        // Dosyayı bir Resource nesnesine dönüştür
        Resource resource = new FileSystemResource(pdfFile);

        // PDF dosyasını yanıt olarak döndür
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + pdfFile.getName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/addPdf")
    public ResponseEntity<PdfInfoModel> setUploadPdf(@RequestBody PdfUploadQueryModel queryModel) {
        PdfInfoModel pdfInfoModel = new PdfInfoModel();
        pdfInfoModel.setSetId(pdfInfoService.add(queryModel));
        return new ResponseEntity<>(pdfInfoModel,HttpStatus.OK);
    }




    @CrossOrigin(origins = "*")
    @PostMapping("/uploadPdf")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file, @RequestParam("Id") String id) {
        System.out.println(id);

        PdfInfoEntity model=pdfInfoService.findById(id);
        if (file.isEmpty()) {
            return new ResponseEntity<>("Dosya boş!", HttpStatus.BAD_REQUEST);
        }


        // PDF dosyasının ismini alın
        String fileName = file.getOriginalFilename();
        // Dosyanın kaydedileceği tam yol

        File destinationFile = new File(UPLOAD_DIR +model.getHomeworkEntity().getId()+"/"+ model.getId()+".pdf");

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
