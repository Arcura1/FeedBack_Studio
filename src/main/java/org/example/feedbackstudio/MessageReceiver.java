package org.example.feedbackstudio;

import com.rabbitmq.client.Channel;
import org.example.feedbackstudio.note.Model.NoteQueryModel;
import org.example.feedbackstudio.note.service.NoteService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;


import java.io.IOException;

@Service
public class MessageReceiver {
    private final NoteService noteService;

    public MessageReceiver(NoteService noteService) {
        this.noteService = noteService;
    }

    @RabbitListener(queues = "hello-queue")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }

//    @RabbitListener(queues = "note")
//    public String add(@RequestBody NoteQueryModel noteDto) {
//        System.out.println("Received: " + noteDto);
//        return "done";
//    }
@RabbitListener(queues = "note-queue")
public void receiveMessage(NoteQueryModel noteDto, Channel channel, Message message) throws IOException {
    try {
        // Mesajı işleme
        this.noteService.add(noteDto);
        System.out.println("Received: " + noteDto);

        // Mesajı başarılı şekilde işledikten sonra onay gönder
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    } catch (Exception e) {
        // Eğer işleme hatası olursa, mesajı tekrar kuyruğa ekle
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        System.err.println("Error processing message: " + e.getMessage());
    }
}
}
