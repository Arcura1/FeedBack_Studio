package org.example.feedbackstudio.note.RestController;

import lombok.Getter;
import org.example.feedbackstudio.MessageSender;
import org.example.feedbackstudio.note.Model.*;
import org.example.feedbackstudio.note.entity.HomeworkEntity;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.example.feedbackstudio.note.repository.NoteRepository;
import org.example.feedbackstudio.note.repository.PdfInfoRepository;
import org.example.feedbackstudio.note.service.HomeworkService;
import org.example.feedbackstudio.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Homework")
@CrossOrigin(origins = "*")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    // Create a new Homework
    @PostMapping("/add")
    public ResponseEntity<HomeworkModel> createHomework(@RequestBody HomeworkQueryModel homework) {
        HomeworkModel createdHomework = homeworkService.createHomework(homework);
        return new ResponseEntity<>(createdHomework, HttpStatus.CREATED);
    }
    @GetMapping("/get")
    public ResponseEntity<HomeworkModel> getHomework(@RequestParam String homeworkId) {
        try {
            HomeworkModel homework = homeworkService.getHomework(homeworkId);
            return new ResponseEntity<>(homework, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Eğer ödev bulunamazsa, 404 Not Found döndürülür
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/put")
    public ResponseEntity<HomeworkQueryModel> updateHomework(@RequestBody HomeworkQueryModel homework) {
        homeworkService.updateHomework(homework);
        return new ResponseEntity<>(homework, HttpStatus.OK);
    }
    @DeleteMapping("/del/{homeworkId}")
    public ResponseEntity<String> deleteHomework(@PathVariable String homeworkId) {
        homeworkService.deleteHomework(homeworkId);
        return new ResponseEntity<>("silindi", HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<HomeworkModel>> getAllHomework() {
        List<HomeworkModel> homeworkList = homeworkService.getAllHomework();
        return new ResponseEntity<>(homeworkList, HttpStatus.OK);
    }


}
