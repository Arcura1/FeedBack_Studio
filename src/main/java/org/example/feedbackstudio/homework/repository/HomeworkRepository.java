package org.example.feedbackstudio.homework.repository;

import org.example.feedbackstudio.homework.entitiy.HomeworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<HomeworkEntity, Long> , JpaSpecificationExecutor<HomeworkEntity> {
    Optional<HomeworkEntity> findById(Long id); // "Id" yerine "id" yazıldı.
    List<HomeworkEntity> findByTeacherId(Long teacherId);
    List<HomeworkEntity> findByClassroomIdAndTeacherId(Long classroomId, Long teacherId);
    List<HomeworkEntity> findByClassroomId( Long classroomId);
    List<HomeworkEntity> findByClassroomIdIn(List<Long> classroomIds);

}
