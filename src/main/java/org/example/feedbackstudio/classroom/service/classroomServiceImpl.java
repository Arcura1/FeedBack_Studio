package org.example.feedbackstudio.classroom.service;

import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.example.feedbackstudio.classroom.model.clasroomQueryModel;
import org.example.feedbackstudio.classroom.repository.ClassroomRepository;
import org.example.feedbackstudio.organization.entity.organizationEntity;
import org.example.feedbackstudio.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class classroomServiceImpl implements classroomService{

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public classroomEntity createClassroom(clasroomQueryModel model) {
        classroomEntity existingClassroom = new classroomEntity();
        existingClassroom.setName(model.getName());
        existingClassroom.setFloor(model.getFloor());
        existingClassroom.setRoomNumber(model.getRoomNumber());
        existingClassroom.setCapacity(model.getCapacity());
        existingClassroom.setHasProjector(model.getHasProjector());
        existingClassroom.setHasWhiteboard(model.getHasWhiteboard());
        existingClassroom.setHasAirConditioning(model.getHasAirConditioning());
        existingClassroom.setDescription(model.getDescription());
        organizationEntity organization = organizationRepository.findById(model.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        existingClassroom.setOrganization(organization);
        return classroomRepository.save(existingClassroom);
    }

    @Override
    public List<classroomEntity> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    @Override
    public Optional<classroomEntity> getClassroomById(Long id) {
        return classroomRepository.findById(id);
    }

    @Override
    public classroomEntity updateClassroom(Long id, clasroomQueryModel updatedClassroom) {
        return classroomRepository.findById(id)
                .map(existingClassroom -> {
                    existingClassroom.setName(updatedClassroom.getName());
                    existingClassroom.setFloor(updatedClassroom.getFloor());
                    existingClassroom.setRoomNumber(updatedClassroom.getRoomNumber());
                    existingClassroom.setCapacity(updatedClassroom.getCapacity());
                    existingClassroom.setHasProjector(updatedClassroom.getHasProjector());
                    existingClassroom.setHasWhiteboard(updatedClassroom.getHasWhiteboard());
                    existingClassroom.setHasAirConditioning(updatedClassroom.getHasAirConditioning());
                    existingClassroom.setDescription(updatedClassroom.getDescription());
                    organizationEntity organization = organizationRepository.findById(updatedClassroom.getOrganizationId())
                            .orElseThrow(() -> new RuntimeException("Organization not found"));
                    existingClassroom.setOrganization(organization);
                    return classroomRepository.save(existingClassroom);
                }).orElseThrow(() -> new RuntimeException("Classroom not found"));
    }

    @Override
    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }
}
