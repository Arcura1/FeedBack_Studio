package org.example.feedbackstudio.classroom.service;

import org.example.feedbackstudio.classroom.entitiy.ClassroomUserEntity;
import org.example.feedbackstudio.classroom.model.ClassroomUserQueyModel;

import java.util.List;
import java.util.Optional;

public interface ClassroomUserService {

    ClassroomUserEntity saveClassroomUser(ClassroomUserQueyModel model);

    List<ClassroomUserEntity> getAllClassroomUsers();

    Optional<ClassroomUserEntity> getClassroomUserById(Long id);

    void deleteClassroomUser(Long id);
    ClassroomUserEntity updateClassroomUser(Long id, ClassroomUserQueyModel model);
    Optional<List<ClassroomUserEntity>> getClassroomUsersByUserId(Long userId);

}
