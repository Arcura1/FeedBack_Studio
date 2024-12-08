package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.note.Model.NoteModel;
import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.converter.NoteConverter;
import org.example.feedbackstudio.note.entity.HomeworkEntity;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.example.feedbackstudio.note.repository.HomeworkRepository;
import org.example.feedbackstudio.note.repository.NoteRepository;
import org.example.feedbackstudio.note.repository.PdfInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private PdfInfoRepository pdfInfoRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;


    @CrossOrigin(origins = "*")
    @Override
    public NoteModel view(String NoteId) {
        NoteEntity findedNote=null;
        findedNote = noteRepository.findById(NoteId);
        if (findedNote!=null) {
            NoteEntity note = findedNote;
            return NoteConverter.convertToModel(note);
        } else {
            throw new RuntimeException("Note not found with ID: " + NoteId);
        }
    }

//    @Override
//    public NoteModel viewByPdf(String pdfId) {
//        NoteEntity findedNote=null;
//        findedNote = noteRepository.findById(NoteId);
//        if (findedNote!=null) {
//            NoteEntity note = findedNote;
//            return NoteConverter.convertToModel(note);
//        } else {
//            throw new RuntimeException("Note not found with ID: " + NoteId);
//        }
//    }
//
//    @Override
//    public NoteModel viewByHomework(String pdfId) {
//        NoteEntity findedNote=null;
//        findedNote = noteRepository.findById(NoteId);
//        if (findedNote!=null) {
//            NoteEntity note = findedNote;
//            return NoteConverter.convertToModel(note);
//        } else {
//            throw new RuntimeException("Note not found with ID: " + NoteId);
//        }
//    }
    //    @Override
//    public NoteModel viewByHomeworkAndPage(String pdfId) {
//        NoteEntity findedNote=null;
//        findedNote = noteRepository.findById(NoteId);
//        if (findedNote!=null) {
//            NoteEntity note = findedNote;
//            return NoteConverter.convertToModel(note);
//        } else {
//            throw new RuntimeException("Note not found with ID: " + NoteId);
//        }
//    }

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
        HomeworkEntity homework = new HomeworkEntity();
        homeworkRepository.save(homework);

        PdfInfoEntity adda = new PdfInfoEntity();

        adda.setHomeworkEntity(homework);
        adda.setHomeworkEntity(null);
        pdfInfoRepository.save(adda);

        add.setPdfInfoEntity(adda);
        add.setId(note.getId());
        add.setXcoordinate(note.getXcoordinate());
        add.setYcoordinate(note.getYcoordinate());
        add.setNote(note.getNote());
        noteRepository.save(add);
        return add.getPdfInfoEntity().getId().toString();
    }
}
