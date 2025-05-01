package org.example.feedbackstudio.login.authority.service;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.example.feedbackstudio.login.authority.entity.Authority;
import org.example.feedbackstudio.login.authority.model.query.AuthorityQueryDTO;
import org.example.feedbackstudio.login.authority.model.query.AuthorityQueryModel;
import org.example.feedbackstudio.login.authority.repository.AuthorityRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority saveAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll();
    }

    @Override
    public Optional<Authority> getAuthorityById(Long id) {
        return authorityRepository.findById(id);
    }

    @Override
    public Authority updateAuthority(Long id, Authority authority) {
        return authorityRepository.findById(id).map(existing -> {
            existing.setName(authority.getName());
            existing.setDescription(authority.getDescription());
            return authorityRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Authority not found with ID: " + id));
    }

    @Override
    public void deleteAuthority(Long id) {
        authorityRepository.deleteById(id);
    }


    @Override
    @Transactional
    public void deleteAllByOrganizationId(Long organizationId) {
        authorityRepository.deleteAllByOrganizationId(organizationId);
    }

    @Override
    @Transactional
    public List<Authority> searchAuthorities(AuthorityQueryDTO dto) {
        Specification<Authority> spec = (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (dto.getOrganizationId() != null) {
                predicates.getExpressions().add(cb.equal(root.get("organizationId"), dto.getOrganizationId()));
            }
            if (dto.getClassroomId() != null) {
                predicates.getExpressions().add(cb.equal(root.get("classroomId"), dto.getClassroomId()));
            }
            if (dto.getClassroomUserId() != null) {
                predicates.getExpressions().add(cb.equal(root.get("classroomUserId"), dto.getClassroomUserId()));
            }
            if (dto.getPdfInfoId() != null) {
                predicates.getExpressions().add(cb.equal(root.get("pdfInfoId"), dto.getPdfInfoId()));
            }
            if (dto.getHomewrokId() != null) {
                predicates.getExpressions().add(cb.equal(root.get("homewrokId"), dto.getHomewrokId()));
            }
            if (dto.getAuthorityType() != null) {
                predicates.getExpressions().add(cb.equal(root.get("authorityType"), dto.getAuthorityType()));
            }
            if (dto.getEffectTypeEnum() != null) {
                predicates.getExpressions().add(cb.equal(root.get("effectTypeEnum"), dto.getEffectTypeEnum()));
            }

            return predicates;
        };

        return authorityRepository.findAll(spec);
    }

    @Override
    public List<Authority> getAuthByRole(Long roleId) {
        return authorityRepository.findAuthoritiesByRoleId(roleId);
    }
}
