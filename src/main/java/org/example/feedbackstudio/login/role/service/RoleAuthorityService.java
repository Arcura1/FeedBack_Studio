package org.example.feedbackstudio.login.role.service;

import org.example.feedbackstudio.login.role.entity.roleAuthorityEntitiy;
import org.example.feedbackstudio.login.role.model.RoleAuthorityRequestModel;

import java.util.List;
import java.util.Optional;

public interface RoleAuthorityService {
    roleAuthorityEntitiy saveRoleAuthority(RoleAuthorityRequestModel model);
    List<roleAuthorityEntitiy> getAllRoleAuthorities();
    Optional<roleAuthorityEntitiy> getRoleAuthorityById(Long id);
    roleAuthorityEntitiy updateRoleAuthority(Long id, RoleAuthorityRequestModel model);
    void deleteRoleAuthority(Long id);
}
