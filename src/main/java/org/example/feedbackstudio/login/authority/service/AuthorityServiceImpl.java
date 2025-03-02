package org.example.feedbackstudio.login.authority.service;

import org.example.feedbackstudio.login.authority.entity.Authority;
import org.example.feedbackstudio.login.authority.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

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
}
