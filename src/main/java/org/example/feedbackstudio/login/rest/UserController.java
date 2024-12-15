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

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userService.isEmailBlacklisted(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(user);
        }
        User savedUser = userService.saveUser(user);

        return ResponseEntity.ok(savedUser);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");



        User user = userService.login(email, password); // Bu metodu UserService'e eklemelisiniz
        if (user != null) {
            return ResponseEntity.ok(user); // Giriş başarılı
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Giriş başarısız
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/blacklist")
    public ResponseEntity<String> addToBlacklist(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.addToBlacklist(email);
        return ResponseEntity.ok("E-posta kara listeye eklendi: " + email);
    }

    @GetMapping("/blacklist/{email}")
    @Cacheable("blacklist")
    public ResponseEntity<String> checkBlacklist(@PathVariable String email) {
        boolean isBlacklisted = userService.isEmailBlacklisted(email);
        if (isBlacklisted) {
            return ResponseEntity.ok("E-posta kara listede.");
        } else {
            return ResponseEntity.ok("E-posta kara listede değil.");
        }
    }
}
