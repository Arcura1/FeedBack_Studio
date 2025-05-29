package org.example.feedbackstudio.note.analyzer.loadbalancer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class QueueContainerScalerService {

    private final PdfProcessorQueueService queueService;
    private final DockerContainerService containerService;

    // Aktif container isimleri
    private final Set<String> activeContainers = new HashSet<>();

    public QueueContainerScalerService(PdfProcessorQueueService queueService,
                                       DockerContainerService containerService) {
        this.queueService = queueService;
        this.containerService = containerService;
    }

    // Her 10 saniyede bir kontrol et
    @Scheduled(fixedRate = 10000)
    public void scaleContainers() {
        int messageCount = queueService.getMessageCount();
        if (messageCount < 0) {
            System.err.println("Mesaj sayısı alınamadı.");
            return;
        }

        // Minimum 1 container olacak şekilde hesaplama
        int requiredContainers = 1 + Math.max(0, (messageCount - 2 + 1) / 2);

        System.out.println("Mesaj sayısı: " + messageCount + ", Gerekli container sayısı: " + requiredContainers);

        // Eksikse yeni container başlat
        while (activeContainers.size() < requiredContainers) {
            int newIndex = activeContainers.size() + 1;
            String containerName = "my-python-app-" + newIndex;

            boolean started = containerService.startContainer(containerName);
            if (started) {
                activeContainers.add(containerName);
            }
        }

        // Fazlaysa durdur
        while (activeContainers.size() > requiredContainers) {
            int toStopIndex = activeContainers.size();
            String containerName = "my-python-app-" + toStopIndex;

            boolean stopped = containerService.stopContainer(containerName);
            if (stopped) {
                activeContainers.remove(containerName);
            }
        }
    }
}
