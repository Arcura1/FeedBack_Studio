package org.example.feedbackstudio.classroom.service;

import jakarta.transaction.Transactional;
import org.example.feedbackstudio.classroom.entitiy.ClassroomUserEntity;
import org.example.feedbackstudio.classroom.model.ClassroomUserQueyModel;
import org.example.feedbackstudio.classroom.repository.ClassroomUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClassroomUserServiceImpl implements ClassroomUserService {

    private final ClassroomUserRepository classroomUserRepository;

    // Constructor Injection
    public ClassroomUserServiceImpl(ClassroomUserRepository classroomUserRepository) {
        this.classroomUserRepository = classroomUserRepository;
    }

    @Override
    public ClassroomUserEntity saveClassroomUser(ClassroomUserQueyModel model) {
        ClassroomUserEntity entity = ClassroomUserEntity.builder()
                .classroomId(model.getClassroomId())
                .userId(model.getUserId())
                .build();
        return classroomUserRepository.save(entity);
    }

    @Override
    public List<ClassroomUserEntity> getAllClassroomUsers() {
        return classroomUserRepository.findAll();
    }

    @Override
    public Optional<ClassroomUserEntity> getClassroomUserById(Long id) {
        return classroomUserRepository.findById(id);
    }

    @Override
    public void deleteClassroomUser(Long id) {
        classroomUserRepository.deleteById(id);
    }

    @Override
    public ClassroomUserEntity updateClassroomUser(Long id, ClassroomUserQueyModel model) {
        Optional<ClassroomUserEntity> existingEntity = classroomUserRepository.findById(id);
        if (existingEntity.isPresent()) {
            ClassroomUserEntity entity = existingEntity.get();
            entity.setClassroomId(model.getClassroomId());
            entity.setUserId(model.getUserId());
            return classroomUserRepository.save(entity);
        }
        throw new RuntimeException("Güncellenecek kayıt bulunamadı!");
    }

    @Override
    public Optional<List<ClassroomUserEntity>> getClassroomUsersByUserId(Long userId) {
        return classroomUserRepository.findAllByUserId(userId);
    }

}
