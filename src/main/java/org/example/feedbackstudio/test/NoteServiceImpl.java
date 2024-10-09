package org.example.feedbackstudio.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService{
    @Autowired
    private NoteRepository noteRepository;


    @Override
    public String view() {
        return "oray";
    }

    @Override
    public String viewAll() {
        return noteRepository.findAll().toString();
    }

    @Override
    public String deleteAll() {
        return "her şey silindi";
    }

    @Override
    public String add(NoteDto note) {
        NoteEntity add= new NoteEntity(note.getId(), note.getEmail(), note.getName());
        noteRepository.save(add);
        return "eklendi";
    }
}
