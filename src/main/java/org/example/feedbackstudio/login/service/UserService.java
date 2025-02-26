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

    /**
     * Kullanıcıyı kaydeder, aynı email ve şifreye sahip kullanıcı varsa kara listeye ekler.
     * @param user Kaydedilecek kullanıcı
     * @return Kaydedilen kullanıcı
     */
    public User saveUser(User user) {
        // Aynı email ve şifre ile kullanıcı var mı kontrol et
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            addToBlacklist(user.getEmail()); // Aynı email ve şifre varsa blackliste ekle
            throw new IllegalArgumentException("Bu e-posta ve şifre zaten kullanılıyor, kullanıcı kara listeye alındı.");
        }
        return userRepository.save(user); // Yeni kullanıcıyı kaydet
    }

    /**
     * Kullanıcıyı ID'ye göre getirir.
     * @param id Kullanıcı ID
     * @return Kullanıcı opsiyonu
     */
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * E-posta ile kullanıcı getirir.
     * @param email E-posta adresi
     * @return Kullanıcı nesnesi
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Kullanıcıyı ID'ye göre siler.
     * @param id Kullanıcı ID
     */
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    /**
     * Kullanıcı girişini doğrular.
     * @param email Kullanıcı email
     * @param password Kullanıcı şifre
     * @return Kullanıcı nesnesi
     */
    public User login(String email, String password) {
        if (isEmailBlacklisted(email)) {
            throw new IllegalArgumentException("Bu e-posta kara listede.");
        }

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
        System.out.println("E-posta kara listeye eklendi: " + email);
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
