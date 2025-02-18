package org.example.feedbackstudio.classroom.repository;

import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<classroomEntity, Long> {


}