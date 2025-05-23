package org.example.feedbackstudio;

import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.analyzer.model.pdfAnalyzerModel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final String QUEUE_NAME = "hello-queue";
    private static final String NOTE_NAME = "note-queue";
    private static final String PDF_QUEUE = "pdf_processor";

    public void sendMessage(String message) {
        amqpTemplate.convertAndSend(QUEUE_NAME, message);
        System.out.println("Sent: " + message);
    }
    public void sendMessageNote(NoteQueryModel noteDto) {
        amqpTemplate.convertAndSend(NOTE_NAME, noteDto);
        System.out.println("Sent: " + noteDto);
    }
//    public void sendMessagePdfProcessor(pdfAnalyzerModel noteDto) {
//        amqpTemplate.convertAndSend(PDF_QUEUE, noteDto);
//        System.out.println("Sent: " + noteDto);
//    }
}