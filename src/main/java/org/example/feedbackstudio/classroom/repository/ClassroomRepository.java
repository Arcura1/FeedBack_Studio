package org.example.feedbackstudio.classroom.repository;

import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<classroomEntity, Long> {
    // Belirli bir kullanıcıya ait tüm derslikleri getir
    List<classroomEntity> findByUserId(Long userId);

    // Organizasyon ID'ye göre sınıfları getir
    List<classroomEntity> findByOrganizationId(Long organizationId);


}