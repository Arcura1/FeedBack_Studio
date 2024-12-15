package org.example.feedbackstudio.login.service;

import org.example.feedbackstudio.login.dao.UserRepository;
import org.example.feedbackstudio.login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";

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

    /**
     * E-posta adresini Redis üzerinden kara listeye ekler.
     * @param email E-posta adresi
     */
    public void addToBlacklist(String email) {
        String key = BLACKLIST_KEY_PREFIX + email;
        redisTemplate.opsForValue().set(key, "BLACKLISTED", 7, TimeUnit.DAYS); // 7 gün geçerli
    }

    /**
     * E-postanın kara listede olup olmadığını kontrol eder.
     * @param email E-posta adresi
     * @return boolean Kara listede mi?
     */
    public boolean isEmailBlacklisted(String email) {
        String key = BLACKLIST_KEY_PREFIX + email;
        return redisTemplate.hasKey(key);
    }
}
