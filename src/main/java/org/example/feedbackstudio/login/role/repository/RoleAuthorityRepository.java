package org.example.feedbackstudio.login.role.repository;

import org.example.feedbackstudio.login.role.entity.roleAuthorityEntitiy;
import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleAuthorityRepository extends JpaRepository<roleAuthorityEntitiy, Long>, JpaSpecificationExecutor<roleEntity> {
    void deleteAllByAuthorityId(Long authorityId);
}
