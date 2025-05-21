package org.example.feedbackstudio.login.role.repository;

import org.example.feedbackstudio.login.role.entity.roleAuthorityEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleAuthorityRepository extends JpaRepository<roleAuthorityEntitiy, Long> {
}
