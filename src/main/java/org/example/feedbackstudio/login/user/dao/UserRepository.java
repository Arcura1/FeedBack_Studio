package org.example.feedbackstudio.login.user.dao;

import org.example.feedbackstudio.login.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods if needed

    User findByEmail(String email);
}
