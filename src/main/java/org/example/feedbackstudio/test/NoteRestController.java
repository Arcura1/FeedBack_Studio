package org.example.feedbackstudio.test;

import org.example.feedbackstudio.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class NoteRestController {

    private final NoteService noteService;

    @Autowired
    public NoteRestController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/viewAll")
    public String viewAll() {
        return noteService.viewAll();
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
