package org.example.feedbackstudio.login.user.rest;

import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.repository.RoleRepository;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.example.feedbackstudio.login.role.service.RoleService;
import org.example.feedbackstudio.login.user.dao.UserRepository;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.login.user.model.UserDTO;
import org.example.feedbackstudio.login.user.model.UserQueryModel;
import org.example.feedbackstudio.login.user.service.UserService;
import org.example.feedbackstudio.login.user.service.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // EKLENDİ
import org.springframework.util.StringUtils; // EKLENDİ (String boş mu kontrolü için)
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
// @CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository; // Bu bağımlılıklar UserController'da gerekli mi?
    // Genellikle servis katmanı bu tür işlemleri yapar.
    @Autowired
    private RoleService roleService;   // UserController'ın doğrudan repository veya başka servislere
    // erişmesi yerine tüm iş mantığını UserService'e devretmesi daha iyi bir tasarım olabilir.
    @Autowired
    private UserRepository userRepository; // UserService üzerinden erişmek daha iyi olur.

    @Autowired
    private PasswordEncoder passwordEncoder; // PasswordEncoder'ı enjekte et

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            // Blacklist kontrolü userService.saveUser içine taşınabilir veya burada kalabilir.
            if (userService.isEmailBlacklisted(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Bu e-posta adresi kara listede: " + user.getEmail());
            }
            User savedUser = userService.saveUser(user); // saveUser şifreyi hash'leyecek
            return ResponseEntity.ok(savedUser); // Genellikle DTO dönmek daha iyi.
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        Optional<List<User>> users = userService.getUsers();
        return users.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<UserDTO>> getUserByType(@PathVariable RoleTypeEnum type) {
        Optional<List<UserDTO>> result = userService.getUserByType(type);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
            // Eğer mevcut şifreleri migrate etmek istiyorsanız loginAndMigratePassword kullanın:
            // UserDTO user = userService.loginAndMigratePassword(email, password);

            // Sadece normal login için:
            UserDTO user = userService.login(email, password);

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("E-posta veya şifre hatalı.");
            }
        } catch (IllegalArgumentException e) { // Kara liste veya diğer iş mantığı hataları için
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
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
        return ResponseEntity.ok(isBlacklisted ? "E-posta kara listede." : "E-posta kara listede değil.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        // Güncelleme işlemini de UserService'e taşımak daha temiz olur.
        // Şimdilik Controller'da bırakıyorum.

        Optional<User> existingUserOptional = userRepository.findById(id); // userService.getUserById(id) daha iyi olur.

        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı.");
        }

        User existingUser = existingUserOptional.get();

        // Alanları güncelle (null kontrolleri eklenebilir)
        if (updatedUser.getFirstName() != null) existingUser.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) existingUser.setLastName(updatedUser.getLastName());

        // E-posta güncelleniyorsa ve yeni e-posta başkası tarafından kullanılmıyorsa
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equalsIgnoreCase(existingUser.getEmail())) {
            if (userRepository.findByEmail(updatedUser.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu e-posta adresi zaten başkası tarafından kullanılıyor.");
            }
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhone() != null) existingUser.setPhone(updatedUser.getPhone()); // Telefon için de unique kontrolü gerekebilir

        // ŞİFRE GÜNCELLEME: Eğer istekte yeni bir şifre varsa (boş değilse), hash'leyerek güncelle
        if (StringUtils.hasText(updatedUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        // Eğer updatedUser.getPassword() null veya boşsa, mevcut şifreye dokunulmaz.

        // Rol güncelleme
        if (updatedUser.getRole() != null) existingUser.setRole(updatedUser.getRole()); // Bu string rolü ne kadar anlamlı? RoleId/RoleEntity daha önemli.
        if (updatedUser.getRoleId() != null) {
            Optional<roleEntity> tempRole = roleService.getRoleById(updatedUser.getRoleId());
            if (tempRole.isPresent()) {
                existingUser.setRoleEntity(tempRole.get());
                existingUser.setRoleId(tempRole.get().getId());
                // String 'role' alanını da senkronize et:
                existingUser.setRole(tempRole.get().getRoleTypeEnum().name());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Belirtilen rol ID (" + updatedUser.getRoleId() + ") ile rol bulunamadı.");
            }
        }

        User savedUser = userRepository.save(existingUser); // userService.saveUser(existingUser) daha iyi olabilir (eğer saveUser içinde ek mantıklar varsa)
        return ResponseEntity.ok(savedUser); // DTO dönmek daha iyi.
    }

    @PostMapping("/search")
    public List<User> searchUsers(@RequestBody UserQueryModel query) {
        // Şifre ile arama (UserSpecifications.hasPassword) BCrypt ile artık doğrudan çalışmaz.
        // Bu özelliği ya kaldırın ya da şifre alanını arama kriterlerinden çıkarın.
        if (StringUtils.hasText(query.getPassword())) {
            // Uyarı loglayabilir veya istemciye bilgi verebilirsiniz.
            System.out.println("UYARI: Şifreye göre arama, güvenlik nedeniyle desteklenmemektedir.");
            query.setPassword(null); // Şifre filtresini devre dışı bırak
        }

        Specification<User> spec = Specification
                .where(UserSpecifications.hasId(query.getId()))
                .and(UserSpecifications.hasFirstName(query.getFirstName()))
                .and(UserSpecifications.hasLastName(query.getLastName()))
                .and(UserSpecifications.hasEmail(query.getEmail()))
                .and(UserSpecifications.hasPhone(query.getPhone()))
                .and(UserSpecifications.hasPassword(query.getPassword())) // Artık null veya etkisiz olacak
                .and(UserSpecifications.hasRole(query.getRole()))
                .and(UserSpecifications.hasRoleId(query.getRoleId()))
                .and(UserSpecifications.hasRoleType(query.getRoleType()));

        return userRepository.findAll(spec);
    }
}