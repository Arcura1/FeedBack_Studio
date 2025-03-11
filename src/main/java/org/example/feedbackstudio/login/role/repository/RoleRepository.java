package org.example.feedbackstudio.login.role.repository;

import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<roleEntity, Long> {
    @Override
    Optional<roleEntity> findById(Long Long);
    boolean existsByRoleTypeEnum(RoleTypeEnum roleTypeEnum);
    roleEntity findByRoleTypeEnum(RoleTypeEnum roleTypeEnum);
}
