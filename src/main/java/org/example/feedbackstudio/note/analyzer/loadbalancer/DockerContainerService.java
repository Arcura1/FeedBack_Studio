package org.example.feedbackstudio.note.analyzer.loadbalancer;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DockerContainerService {

    private final String imageName = "my-python-app";
    private final String networkName = "my-network";

    public boolean startContainer(String containerName) {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "docker", "run", "-d", // arka planda çalışsın
                    "--name", containerName,
                    "--network", networkName,
                    imageName
            );
            builder.inheritIO();
            Process process = builder.start();
            return process.isAlive();
        } catch (IOException e) {
            System.err.println("Başlatılamadı: " + containerName);
            return false;
        }
    }

    public boolean stopContainer(String containerName) {
        try {
            ProcessBuilder builder = new ProcessBuilder("docker", "rm", "-f", containerName);
            builder.inheritIO();
            Process process = builder.start();
            return process.waitFor() == 0;
        } catch (Exception e) {
            System.err.println("Durdurulamadı: " + containerName);
            return false;
        }
    }
}
