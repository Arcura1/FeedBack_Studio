package org.example.feedbackstudio.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService{
    @Autowired
    private NoteRepository noteRepository;


    @Override
    public String view() {
        return "oray";
    }

    @Override
    public List<String> viewAll() {
        List<String> result = new ArrayList<String>();
        Iterable<NoteEntity> oray =noteRepository.findAll();
        oray.forEach((val)-> {
            System.out.println(val);
            result.add(val.getId());

        });
        return result;
    }

    @Override
    public String deleteAll() {
        noteRepository.deleteAll();
        return "her şey silindi";
    }

    @Override
    public String add(NoteDto note) {
        NoteEntity add= new NoteEntity();
        add.setId(note.getId());
        add.setXcoordinate(note.getXcoordinate());
        add.setYcoordinate(note.getYcoordinate());
        noteRepository.save(add);
        return "eklendi";
    }
}
