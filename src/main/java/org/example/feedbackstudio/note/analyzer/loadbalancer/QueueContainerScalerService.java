package org.example.feedbackstudio.note.analyzer.loadbalancer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class QueueContainerScalerService {

    private final PdfProcessorQueueService queueService;
    private final DockerContainerService containerService;

    // Hangi container'lar açık, takip ediyoruz
    private final Set<String> activeContainers = new HashSet<>();

    public QueueContainerScalerService(PdfProcessorQueueService queueService,
                                       DockerContainerService containerService) {
        this.queueService = queueService;
        this.containerService = containerService;
    }

    // Her 10 saniyede bir kontrol eder
    @Scheduled(fixedRate = 10000)
    public void scaleContainers() {
        int messageCount = queueService.getMessageCount();
        if (messageCount < 0) {
            System.err.println("Mesaj sayısı alınamadı.");
            return;
        }

        // Hesaplama: 2 mesaj için 2 container, sonra her +2 mesaj için +1 container
        int requiredContainers = 2 + Math.max(0, (messageCount - 2 + 1) / 2);

        System.out.println("Mesaj sayısı: " + messageCount + ", Gerekli container sayısı: " + requiredContainers);

        // Açık container sayısı yetersizse, eksik olanları başlat
        while (activeContainers.size() < requiredContainers) {
            int newIndex = activeContainers.size() + 1;
            String containerName = "my-python-app-" + newIndex;

            boolean started = containerService.startContainer(containerName);
            if (started) {
                activeContainers.add(containerName);
            }
        }

        // Gerekenden fazla container varsa, fazla olanları durdur (manuel olarak burada `--rm` kullanıyorsan bu adım opsiyonel olur)
        while (activeContainers.size() > requiredContainers) {
            int toStopIndex = activeContainers.size();
            String containerName = "my-python-app-" + toStopIndex;
            boolean stopped = containerService.stopContainer(containerName); // docker stop + remove
            if (stopped) {
                activeContainers.remove(containerName);
            }
        }
    }
}
