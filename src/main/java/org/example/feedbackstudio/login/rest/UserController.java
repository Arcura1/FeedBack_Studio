package org.example.feedbackstudio.login.rest;

import org.example.feedbackstudio.login.entity.User;
import org.example.feedbackstudio.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Kullanıcı oluşturma
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Blacklist kontrolü
        if (userService.isEmailBlacklisted(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bu e-posta adresi kara listede: " + user.getEmail());
        }

        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // Kullanıcı ID'ye göre getirme
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Kullanıcıyı e-posta ile getirme
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // Kullanıcı girişi
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        if (userService.isEmailBlacklisted(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bu e-posta adresi kara listede: " + email);
        }

        User user = userService.login(email, password);
        if (user != null) {
            return ResponseEntity.ok(user); // Giriş başarılı
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("E-posta veya şifre hatalı.");
        }
    }

    // Kullanıcıyı ID'ye göre silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // E-postayı kara listeye ekleme
    @PostMapping("/blacklist")
    public ResponseEntity<String> addToBlacklist(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.addToBlacklist(email);
        return ResponseEntity.ok("E-posta kara listeye eklendi: " + email);
    }

    // Kara listeyi kontrol etme
    @GetMapping("/blacklist/{email}")
    @Cacheable("blacklist") // Redis cache kullanımı
    public ResponseEntity<String> checkBlacklist(@PathVariable String email) {
        boolean isBlacklisted = userService.isEmailBlacklisted(email);
        if (isBlacklisted) {
            return ResponseEntity.ok("E-posta kara listede.");
        } else {
            return ResponseEntity.ok("E-posta kara listede değil.");
        }
    }
}
