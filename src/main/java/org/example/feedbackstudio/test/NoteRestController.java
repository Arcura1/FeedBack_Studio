package org.example.feedbackstudio.test;

import org.example.feedbackstudio.User;
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

    @Autowired
    public NoteRestController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/viewAll")
    public ResponseEntity<List<NoteEntity>> viewAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(noteService.viewAll(), headers, HttpStatus.OK);
    }

    @GetMapping("/view")
    public String view() {
        return noteService.view();
    }

    @PutMapping("/add")
    public String add(@RequestBody NoteDto noteDto) {

        return this.noteService.add(noteDto);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody NoteDto noteDto) {
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
