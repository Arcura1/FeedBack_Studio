package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.login.user.dao.UserRepository;
import org.example.feedbackstudio.note.Model.NoteModel;
import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.converter.NoteConverter;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.pdfInfo.service.PdfInfoService;
import org.example.feedbackstudio.homework.repository.HomeworkRepository;
import org.example.feedbackstudio.note.repository.NoteRepository;
import org.example.feedbackstudio.note.pdfInfo.repository.PdfInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private PdfInfoRepository pdfInfoRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private PdfInfoService pdfInfoService;
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @Override
    public NoteModel view(Long NoteId) {
        NoteEntity findedNote=null;
        findedNote = noteRepository.findById(NoteId).get();
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

        add.setPdfInfoEntity(pdfInfoService.findById(note.getPdfInfoEntityId()));
        add.setPdfInfoEntityId(note.getPdfInfoEntityId());
        add.setUserId(note.getUserId());
        userRepository.findById(note.getUserId())
                .ifPresentOrElse(
                        add::setUser,
                        () -> {
                            throw new IllegalArgumentException("User with ID " + note.getUserId() + " not found");
                        }
                );
        add.setId(note.getId());
        add.setXcoordinate(note.getXcoordinate());
        add.setYcoordinate(note.getYcoordinate());
        add.setNote(note.getNote());
        add.setPage(note.getPage());
        add.setNote(note.getNote());
        add.setTitle(note.getTitle());
        noteRepository.save(add);
        return add.getPdfInfoEntity().getId().toString();
    }

    @Override
    public List<NoteEntity> viewByPdfId(Long pdf) {
        List<NoteEntity> result = new LinkedList<NoteEntity>();
        result=noteRepository.findByPdfInfoEntityId(pdf);

        return result;
    }

    @Override
    public List<NoteEntity> viewByPdfInfo(Long id) {
        List<NoteEntity> result=new ArrayList<>();
        noteRepository.findByPdfInfoEntityId(id);
        result.addAll(noteRepository.findByPdfInfoEntityId(id));
        return result;

//        List<NoteEntity> deneme = StreamSupport.stream(noteRepository.findAll().spliterator(), false)
//                .collect(Collectors.toList());
//        deneme.forEach((val)->{
//            if(val.getPdfInfoEntity().getId().toString().equals(id)) {
//                result.add(val);
//            }
//        });
//        result=noteRepository.findByPdfInfoEntity(pdfInfoRepository.findById(id));
//        result=noteRepository.findByPdfInfoEntityId(id);
//        return result;
    }

    @Override
    public String delByPdfinfo(Long id) {
        List<NoteEntity> result=new ArrayList<>();
        List<NoteEntity> deneme =noteRepository.findByPdfInfoEntityId(id);
        deneme.forEach((val)->{
            if(val.getPdfInfoEntity().getId().equals(id)) {
                noteRepository.delete(val);
            }
        });
        return "Done";
    }

    @Override
    public String analyzeByPdfinfo(Long id) {
        return "";
    }
}
