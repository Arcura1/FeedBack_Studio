package org.example.feedbackstudio.classroom.service;

import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.example.feedbackstudio.classroom.model.clasroomQueryModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface classroomService {
    classroomEntity createClassroom(clasroomQueryModel model);
    List<classroomEntity> getAllClassrooms();
    Optional<classroomEntity> getClassroomById(Long id);
    classroomEntity updateClassroom(Long id, clasroomQueryModel updatedClassroom);
    void deleteClassroom(Long id);
}
