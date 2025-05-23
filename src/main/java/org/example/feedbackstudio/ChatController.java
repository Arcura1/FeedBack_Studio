package org.example.feedbackstudio;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/hello") // Client "/app/hello" adresine mesaj gönderir
    @SendTo("/topic/greetings") // Gelen mesaj "/topic/greetings" dinleyenlere yayınlanır
    public String greeting(String message) throws Exception {
        Thread.sleep(500); // Simülasyon
        return "Merhaba, " + message + "!";
    }
}
