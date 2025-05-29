package org.example.feedbackstudio.note.analyzer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.feedbackstudio.RabbitConfig;
import org.example.feedbackstudio.homework.repository.HomeworkRepository;
import org.example.feedbackstudio.login.user.dao.UserRepository;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.note.analyzer.model.pdfAnalyzerModel;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.pdfInfo.entitiy.PdfInfoEntity;
import org.example.feedbackstudio.note.pdfInfo.repository.PdfInfoRepository;
import org.example.feedbackstudio.note.repository.NoteRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class pdfAnalyzerServiceImpl implements pdfAnalayzerService {

    private final String UPLOAD_DIR = "src/main/resources/static/";

    @Autowired
    private PdfInfoRepository pdfInfoRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public pdfAnalyzerServiceImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public String analayzePdf(Long pdfId, Long userId) {
        // Kullanıcı kontrolü
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        // PDF kontrolü
        Optional<PdfInfoEntity> optionalPdfInfo = pdfInfoRepository.findById(pdfId);
        if (optionalPdfInfo.isEmpty()) {
            return "PDF not found";
        }else if(optionalPdfInfo.get().getAnalayzed()!=null&&optionalPdfInfo.get().getAnalayzed()==true){
            return "It is already Analayzed";
        }

        PdfInfoEntity pdfInfoEntity = optionalPdfInfo.get();

        // PDF dosyasını oku
        File pdfFile = new File(UPLOAD_DIR + pdfInfoEntity.getHomeworkEntity().getId() + "/" + pdfInfoEntity.getId() + ".pdf");
        if (!pdfFile.exists()) {
            return "File not found";
        }

        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(pdfFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading file";
        }

        // Base64 encode
        String encoded = Base64.getEncoder().encodeToString(fileContent);

        // Mesajı oluştur
        pdfAnalyzerModel queue = new pdfAnalyzerModel();
        queue.setUserId(userId);
        queue.setPdfInfoEntityId(pdfId);
        queue.setEncoded(encoded);
        queue.setTitle(pdfInfoEntity.getHomeworkEntity().getTitle());
        queue.setConf(pdfInfoEntity.getHomeworkEntity().getDescription());
        queue.setExamples(getExamplesForqueue(pdfId));
        // RabbitMQ'ya gönder
        try {
            String jsonMessage = objectMapper.writeValueAsString(queue);
            rabbitTemplate.convertAndSend(RabbitConfig.PDF_QUEUE, jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error sending message to queue";
        }

        return "PDF processing request sent.";
    }
    private List<String> getExamplesForqueue(Long pdfId) {
        List<String> examples = new ArrayList<>();

        Optional<Long> homeworkIdOpt = pdfInfoRepository.findById(pdfId)
                .map(PdfInfoEntity::getHomeworkEntityId);

        if (!homeworkIdOpt.isPresent()) {
            return examples;
        }

        Long homeworkId = homeworkIdOpt.get();

        List<PdfInfoEntity> pdfList = pdfInfoRepository.findByhomeworkEntity_id(homeworkId);

        // Güvenli şekilde pdfId'li olanı çıkar
        pdfList.removeIf(pdf -> pdf.getId().equals(pdfId));

        for (PdfInfoEntity pdfInfo : pdfList) {
            List<NoteEntity> notes = noteRepository.findByPdfInfoEntityId(pdfInfo.getId());

            for (NoteEntity note : notes) {
                // İfade ve açıklama varsa örnek formatta ekle
                String formatted = note.getNote();
                examples.add(formatted);
            }
        }

        return examples;
    }
}
