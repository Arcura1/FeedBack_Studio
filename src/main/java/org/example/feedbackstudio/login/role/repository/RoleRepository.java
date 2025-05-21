package org.example.feedbackstudio.login.role.repository;

import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<roleEntity, Long>, JpaSpecificationExecutor<roleEntity> {
    @Override
    Optional<roleEntity> findById(Long Long);
    boolean existsByRoleTypeEnum(RoleTypeEnum roleTypeEnum);
    roleEntity findByRoleTypeEnum(RoleTypeEnum roleTypeEnum);
    Optional<List<roleEntity>> getAllByRoleTypeEnum(RoleTypeEnum roleTypeEnum);
    void deleteAllByOrganizationId(Long organizationId);
    roleEntity findByRoleTypeEnumAndOrganizationId(RoleTypeEnum roleTypeEnum, Long organizationId);
}
