package org.example.feedbackstudio.login.role.repository;

import org.example.feedbackstudio.classroom.entitiy.ClassroomUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleAuthorityRepository extends JpaRepository<ClassroomUserEntity, Long> {
}
