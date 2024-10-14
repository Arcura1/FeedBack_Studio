package org.example.feedbackstudio.login.service;

import org.example.feedbackstudio.login.dao.UserRepository;
import org.example.feedbackstudio.login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public User login(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) { // Şifre kontrolü
            return user; // Kullanıcı bulunursa döndür
        }
        return null; // Kullanıcı bulunamazsa veya şifre yanlışsa null döndür
    }
}