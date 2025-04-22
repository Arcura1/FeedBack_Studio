package org.example.feedbackstudio.login.authority.repository;

import org.example.feedbackstudio.login.authority.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {
    void deleteAllByClassroomId(Long classroomId);
    void deleteAllByOrganizationId(Long organizationId);
}
