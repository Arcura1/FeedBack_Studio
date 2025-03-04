package org.example.feedbackstudio.classroom.repository;

import org.example.feedbackstudio.classroom.entitiy.ClassroomUserEntity;
import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomUserRepository extends JpaRepository<ClassroomUserEntity, Long> {
}
