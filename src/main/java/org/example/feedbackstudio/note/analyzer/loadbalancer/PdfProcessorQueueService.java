package org.example.feedbackstudio.note.analyzer.loadbalancer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class PdfProcessorQueueService {

    private final ConnectionFactory connectionFactory;

    public PdfProcessorQueueService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public int getMessageCount() {
        try (Connection connection = connectionFactory.createConnection();
             Channel channel = connection.createChannel(false)) {

            return channel.queueDeclarePassive("pdf_processor").getMessageCount();

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // hata durumunda -1 döndür
        }
    }
}
