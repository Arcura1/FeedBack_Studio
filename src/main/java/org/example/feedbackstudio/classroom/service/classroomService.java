package org.example.feedbackstudio.classroom.service;

import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.example.feedbackstudio.classroom.model.ClasroomQueryModel;
import org.example.feedbackstudio.classroom.model.query.ClassroomQueryModel;

import java.util.List;
import java.util.Optional;

public interface classroomService {
    classroomEntity createClassroom(ClasroomQueryModel model);
    List<classroomEntity> getAllClassrooms();
    Optional<classroomEntity> getClassroomById(Long id);
    classroomEntity updateClassroom(Long id, ClasroomQueryModel updatedClassroom);
    void deleteClassroom(Long id);
    List<classroomEntity> getClassroomsByUserId(Long userId);
    List<classroomEntity> searchClassrooms(ClassroomQueryModel queryModel);
}
