package org.example.feedbackstudio.login.role.repository;

import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<roleEntity, Long> {
}
