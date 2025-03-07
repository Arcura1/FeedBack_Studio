package org.example.feedbackstudio.login.role.service;

import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public roleEntity saveRole(roleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public List<roleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<roleEntity> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public roleEntity updateRole(Long id, roleEntity role) {
        return roleRepository.findById(id).map(existing -> {
            existing.setName(role.getName());
            existing.setDescription(role.getDescription());
            existing.setRoleTypeEnum(role.getRoleTypeEnum());
            return roleRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
