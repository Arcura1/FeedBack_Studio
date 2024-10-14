package org.example.feedbackstudio.login.dao;

import org.example.feedbackstudio.login.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Custom query methods if needed
    User findByEmail(String email);
}
