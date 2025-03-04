package org.example.feedbackstudio.login.authority.repository;

import org.example.feedbackstudio.login.authority.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
