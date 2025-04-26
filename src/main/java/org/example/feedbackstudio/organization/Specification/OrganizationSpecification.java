package org.example.feedbackstudio.organization.Specification;

import jakarta.persistence.criteria.Predicate;
import org.example.feedbackstudio.organization.entity.organizationEntity;
import org.example.feedbackstudio.organization.model.OrganizationQueryDTO;
import org.springframework.data.jpa.domain.Specification;


public class OrganizationSpecification {

    public static Specification<organizationEntity> filter(OrganizationQueryDTO query) {
        return (root, queryCriteria, builder) -> {
            Predicate predicate = builder.conjunction(); // Başlangıçta boş bir predicate

            if (query.getName() != null) {
                predicate = builder.and(predicate, builder.like(root.get("name"), "%" + query.getName() + "%"));
            }

            if (query.getAddress() != null) {
                predicate = builder.and(predicate, builder.like(root.get("address"), "%" + query.getAddress() + "%"));
            }

            if (query.getEmail() != null) {
                predicate = builder.and(predicate, builder.like(root.get("email"), "%" + query.getEmail() + "%"));
            }

            return predicate;
        };
    }
}
