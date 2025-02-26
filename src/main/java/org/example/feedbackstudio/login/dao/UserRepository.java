package org.example.feedbackstudio.login.dao;

import org.example.feedbackstudio.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods if needed

    User findByEmail(String email);
}
