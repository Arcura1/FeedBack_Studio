package org.example.feedbackstudio.login.role.service;

import org.example.feedbackstudio.login.authority.entity.Authority;
import org.example.feedbackstudio.login.authority.repository.AuthorityRepository;
import org.example.feedbackstudio.login.role.entity.roleAuthorityEntitiy;
import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.model.RoleAuthorityRequestModel;
import org.example.feedbackstudio.login.role.repository.RoleAuthorityRepository;
import org.example.feedbackstudio.login.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleAuthorityServiceImpl implements RoleAuthorityService {

    private final RoleAuthorityRepository roleAuthorityRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    public RoleAuthorityServiceImpl(RoleAuthorityRepository roleAuthorityRepository,
                                    RoleRepository roleRepository,
                                    AuthorityRepository authorityRepository) {
        this.roleAuthorityRepository = roleAuthorityRepository;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public roleAuthorityEntitiy saveRoleAuthority(RoleAuthorityRequestModel model) {
        roleAuthorityEntitiy roleAuthorityEntity = new roleAuthorityEntitiy();
        roleAuthorityEntity.setAuthorityId(model.getAuthorityId());
        roleAuthorityEntity.setRoleId(model.getRoleId());
        return roleAuthorityRepository.save(roleAuthorityEntity);
    }

    @Override
    public List<roleAuthorityEntitiy> getAllRoleAuthorities() {
        return roleAuthorityRepository.findAll();
    }

    @Override
    public Optional<roleAuthorityEntitiy> getRoleAuthorityById(Long id) {
        return roleAuthorityRepository.findById(id);
    }

    @Override
    public roleAuthorityEntitiy updateRoleAuthority(Long id, RoleAuthorityRequestModel model) {
        return roleAuthorityRepository.findById(id).map(existing -> {
            roleEntity role = roleRepository.findById(model.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            Authority authority = authorityRepository.findById(model.getAuthorityId())
                    .orElseThrow(() -> new RuntimeException("Authority not found"));

            existing.setRole(role);
            existing.setRoleId(model.getRoleId());
            existing.setAuthority(authority);
            existing.setAuthorityId(model.getAuthorityId());

            return roleAuthorityRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("RoleAuthority not found with ID: " + id));
    }

    @Override
    public void deleteRoleAuthority(Long id) {
        roleAuthorityRepository.deleteById(id);
    }
}
