package org.example.feedbackstudio.login.authority.service;

import org.example.feedbackstudio.login.authority.entity.Authority;
import org.example.feedbackstudio.login.authority.model.query.AuthorityQueryModel;

import java.util.List;
import java.util.Optional;

public interface AuthorityService {
    Authority saveAuthority(Authority authority);
    List<Authority> getAllAuthorities();
    Optional<Authority> getAuthorityById(Long id);
    Authority updateAuthority(Long id, Authority authority);
    void deleteAuthority(Long id);
    List<Authority> queryAuthorities(AuthorityQueryModel queryModel);
    void deleteAllByOrganizationId(Long organizationId);

}
