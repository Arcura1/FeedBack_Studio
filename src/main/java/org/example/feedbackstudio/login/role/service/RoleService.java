package org.example.feedbackstudio.login.role.service;

import org.example.feedbackstudio.login.role.entity.roleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    roleEntity saveRole(roleEntity role);
    List<roleEntity> getAllRoles();
    Optional<roleEntity> getRoleById(Long id);
    roleEntity updateRole(Long id, roleEntity role);
    void deleteRole(Long id);
}
