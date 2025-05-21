package org.example.feedbackstudio.organization.repository;

import org.example.feedbackstudio.organization.entity.organizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationRepository extends JpaRepository<organizationEntity, Long>, JpaSpecificationExecutor<organizationEntity> {
}
