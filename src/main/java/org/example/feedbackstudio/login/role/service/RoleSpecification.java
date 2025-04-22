package org.example.feedbackstudio.login.role.service;

import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.model.RoleQueryRequest;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {

    public static Specification<roleEntity> filterBy(RoleQueryRequest request) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (request.getName() != null && !request.getName().isEmpty()) {
                predicates.getExpressions().add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
                predicates.getExpressions().add(cb.like(cb.lower(root.get("description")), "%" + request.getDescription().toLowerCase() + "%"));
            }
            if (request.getRoleTypeEnum() != null && !request.getRoleTypeEnum().isEmpty()) {
                predicates.getExpressions().add(cb.equal(root.get("roleTypeEnum"), RoleTypeEnum.valueOf(request.getRoleTypeEnum())));
            }
            if (request.getOrganizationId() != null) {
                predicates.getExpressions().add(cb.equal(root.get("organizationId"), request.getOrganizationId()));
            }

            return predicates;
        };
    }
}
