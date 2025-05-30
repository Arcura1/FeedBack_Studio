package org.example.feedbackstudio.login.authority.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.feedbackstudio.login.authority.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {
    void deleteAllByClassroomId(Long classroomId);
    void deleteAllByOrganizationId(Long organizationId);
    void deleteAllByPdfInfoId(Long pdfInfoId);
    List<Authority> findAllByPdfInfoId(Long pdfInfoId);

    @Query(value = """
    SELECT a.*
    FROM authorities a
    JOIN role_authority ra ON ra.authority_id = a.id
    WHERE ra.role_id = :roleId
""", nativeQuery = true)
    List<Authority> findAuthoritiesByRoleId(@Param("roleId") Long roleId);

}
