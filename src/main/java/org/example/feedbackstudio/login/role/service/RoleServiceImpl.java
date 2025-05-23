package org.example.feedbackstudio.login.role.service;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.model.RoleQueryRequest;
import org.example.feedbackstudio.login.role.repository.RoleRepository;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public Optional<List<roleEntity>> getRolesByRoleType(String roleType) {
        return roleRepository.getAllByRoleTypeEnum(RoleTypeEnum.valueOf(roleType));
    }

    @Override
    public List<roleEntity> queryRoles(RoleQueryRequest request) {
        Specification<roleEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }

            if (request.getDescription() != null) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + request.getDescription().toLowerCase() + "%"));
            }

            if (request.getRoleTypeEnum() != null) {
                predicates.add(cb.equal(root.get("roleTypeEnum"), request.getRoleTypeEnum()));
            }

            if (request.getOrganizationId() != null) {
                predicates.add(cb.equal(root.get("organizationId"), request.getOrganizationId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return roleRepository.findAll(spec);
    }


    @Override
    @Transactional
    public void deleteAllByOrganizationId(Long organizationId) {
        roleRepository.deleteAllByOrganizationId(organizationId);
    }


}
