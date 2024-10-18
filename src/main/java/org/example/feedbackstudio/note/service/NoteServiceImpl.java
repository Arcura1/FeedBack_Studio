package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.note.Model.NoteModel;
import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;


    @Override
    public NoteModel view() {
        NoteModel result = new NoteModel();


        return result;
    }

    @Override
    public List<NoteEntity> viewAll() {
        List<NoteEntity> result = new ArrayList<NoteEntity>();
        Iterable<NoteEntity> oray = noteRepository.findAll();
        oray.forEach((val) -> {

            result.add(val);

        });
        return result;
    }

    @Override
    public String deleteAll() {
        noteRepository.deleteAll();
        return "her şey silindi";
    }

    @Override
    public String add(NoteQueryModel note) {
        NoteEntity add = new NoteEntity();
        add.setId(note.getId());
        add.setXcoordinate(note.getXcoordinate());
        add.setYcoordinate(note.getYcoordinate());
        add.setPdfId(note.getPdfId());
        add.setNote(note.getNote());
        noteRepository.save(add);
        return add.getPdfId().toString();
    }
}
