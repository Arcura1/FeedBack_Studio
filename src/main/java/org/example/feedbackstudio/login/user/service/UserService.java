package org.example.feedbackstudio.login.user.service;

import org.example.feedbackstudio.login.role.entity.roleEntity; // Gerekirse import edin
import org.example.feedbackstudio.login.role.repository.RoleRepository;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.example.feedbackstudio.login.user.dao.UserRepository;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.login.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder; // EKLENDİ
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Gerekirse import edin

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // PasswordEncoder'ı enjekte et

    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";

    /**
     * Kullanıcıyı kaydeder. E-posta zaten varsa hata fırlatır.
     * Şifre BCrypt ile hashlenerek kaydedilir.
     *
     * @param user Kaydedilecek kullanıcı (ham şifre ile)
     * @return Kaydedilen kullanıcı (hashlenmiş şifre ile)
     */
    @Transactional // Birden fazla DB işlemi varsa kullanışlı
    public User saveUser(User user) {
        // E-posta adresi zaten kayıtlı mı kontrol et
        User existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserByEmail != null) {
            // Not: Kara liste mantığınızı burada yeniden değerlendirin.
            // Sadece "e-posta zaten kayıtlı" demek daha yaygın bir yaklaşımdır.
            // Eğer aynı email ve ŞİFRE kombinasyonu için özel bir kara liste mantığınız varsa,
            // bu, şifreler hash'lendikten sonra doğrudan karşılaştırılamaz.
            // addToBlacklist(user.getEmail()); // Bu satırı mevcut mantığınıza göre koruyun veya kaldırın
            throw new IllegalArgumentException("Bu e-posta adresi zaten kayıtlı: " + user.getEmail());
        }

        // Kullanıcının girdiği ham şifreyi BCrypt ile hash'le
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Rol ataması
        if (user.getRoleId() == null && user.getRole() != null) {
            try {
                RoleTypeEnum roleEnum = RoleTypeEnum.valueOf(user.getRole().toUpperCase()); // Enum'a çevir
                // Organizasyon ID'si null olan GUEST rolünü bulmaya çalışıyoruz.
                // Eğer organizasyon ID'si gerektiren başka roller varsa, bu mantığı genişletmeniz gerekebilir.
                roleEntity guestRole = roleRepository.findByRoleTypeEnumAndOrganizationId(roleEnum, null);
                if (guestRole != null) {
                    user.setRoleId(guestRole.getId());
                    user.setRoleEntity(guestRole); // roleEntity'yi de set edelim
                } else if (Objects.equals(user.getRole(), "GUEST")) { // Sadece GUEST için özel durum
                    // GUEST rolü için varsayılan bir rol oluşturma veya bulma mantığı eklenebilir.
                    // Şimdilik, eğer bulunamazsa bir hata fırlatabilir veya null bırakılabilir.
                    // Örnek: throw new RuntimeException("GUEST rolü için tanımlama bulunamadı.");
                }
                // Diğer roller için roleId'nin istekte gelmesi beklenir.
            } catch (IllegalArgumentException e) {
                // user.getRole() geçerli bir RoleTypeEnum değilse
                throw new IllegalArgumentException("Geçersiz rol tipi: " + user.getRole());
            }
        } else if (user.getRoleId() != null) {
            // roleId geldiyse, roleEntity'yi de set etmeye çalışalım
            roleRepository.findById(user.getRoleId()).ifPresent(user::setRoleEntity);
        }


        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? Optional.empty() : Optional.of(users);
    }

    public Optional<List<UserDTO>> getUserByType(RoleTypeEnum type) {
        List<User> users = userRepository.findByRoleName(type);

        if (users.isEmpty()) {
            return Optional.empty();
        }

        List<UserDTO> userDTOs = users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getRoleEntity() != null ? user.getRoleEntity().getRoleTypeEnum().name() : user.getRole(), // RoleEntity'den al
                        null, // 'create' alanı DTO'da ne amaçla kullanılıyor?
                        user.getRoleEntity() != null ? user.getRoleEntity().getOrganizationId() : null // Null check
                ))
                .collect(Collectors.toList());

        return Optional.of(userDTOs);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Kullanıcı girişini doğrular.
     * Girilen ham şifreyi, veritabanındaki hashlenmiş şifre ile karşılaştırır.
     *
     * @param email    Kullanıcı email
     * @param rawPassword Kullanıcının girdiği ham şifre
     * @return UserDTO nesnesi veya null (giriş başarısızsa)
     */
    public UserDTO login(String email, String rawPassword) {
        if (isEmailBlacklisted(email)) {
            // Controller'da yakalanıp uygun HTTP yanıtı verilecek.
            throw new IllegalArgumentException("Bu e-posta kara listede.");
        }

        User user = userRepository.findByEmail(email); // getUserByEmail(email) de kullanılabilir

        // Kullanıcı bulunduysa ve girilen ham şifre, veritabanındaki hashlenmiş şifre ile eşleşiyorsa
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            UserDTO u = new UserDTO();
            u.setId(user.getId());
            u.setEmail(user.getEmail());
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setCreate(null); // Bu alanın amacı nedir?
            if (user.getRoleEntity() != null) { // Null check
                u.setOrganizationId(user.getRoleEntity().getOrganizationId());
                u.setRole(user.getRoleEntity().getRoleTypeEnum().toString());
            } else if (user.getRole() != null) { // Fallback to string role if RoleEntity is null
                u.setRole(user.getRole());
            }
            return u;
        }
        return null; // Kullanıcı bulunamazsa veya şifre yanlışsa null döndür
    }


    // --- MEVCUT ŞİFRELERİ TAŞIMAK İÇİN (LAZY MIGRATION) ---
    // Bu metodu, login metodunuzu değiştirerek veya bir admin arayüzü ile çağırarak kullanabilirsiniz.
    // Şimdilik login içinde bir örnek veriyorum.
    // Not: Bu sadece bir örnektir. Veritabanınızdaki mevcut şifrelerin düz metin olduğunu varsayar.
    // Eğer farklı bir zayıf hash (MD5, SHA1 vb.) kullanılıyorsa, o hash ile karşılaştırma yapmanız gerekir.
    public UserDTO loginAndMigratePassword(String email, String rawPassword) {
        if (isEmailBlacklisted(email)) {
            throw new IllegalArgumentException("Bu e-posta kara listede.");
        }

        User user = userRepository.findByEmail(email);

        if (user != null) {
            String storedPassword = user.getPassword();
            boolean migrationNeeded = !storedPassword.startsWith("$2a$") &&
                    !storedPassword.startsWith("$2b$") &&
                    !storedPassword.startsWith("$2y$"); // BCrypt prefix kontrolü

            boolean passwordMatches;
            if (migrationNeeded) {
                // Eski şifre (düz metin olduğunu varsayıyoruz) ile kontrol
                passwordMatches = storedPassword.equals(rawPassword);
                if (passwordMatches) {
                    // Şifre doğru, BCrypt ile hash'le ve güncelle
                    user.setPassword(passwordEncoder.encode(rawPassword));
                    userRepository.save(user);
                    System.out.println("User " + email + " password migrated to BCrypt.");
                }
            } else {
                // Şifre zaten BCrypt formatında, normal kontrol
                passwordMatches = passwordEncoder.matches(rawPassword, storedPassword);
            }

            if (passwordMatches) {
                UserDTO u = new UserDTO();
                // ... (UserDTO doldurma kodları yukarıdaki login metoduyla aynı)
                u.setId(user.getId());
                u.setEmail(user.getEmail());
                u.setFirstName(user.getFirstName());
                u.setLastName(user.getLastName());
                u.setCreate(null);
                if (user.getRoleEntity() != null) {
                    u.setOrganizationId(user.getRoleEntity().getOrganizationId());
                    u.setRole(user.getRoleEntity().getRoleTypeEnum().toString());
                } else if (user.getRole() != null) {
                    u.setRole(user.getRole());
                }
                return u;
            }
        }
        return null;
    }
    // --- MEVCUT ŞİFRELERİ TAŞIMAK İÇİN (LAZY MIGRATION) - SON ---


    public void addToBlacklist(String email) {
        String key = BLACKLIST_KEY_PREFIX + email;
        redisTemplate.opsForValue().set(key, "BLACKLISTED", 7, TimeUnit.DAYS);
        System.out.println("E-posta kara listeye eklendi: " + email);
    }

    public boolean isEmailBlacklisted(String email) {
        String key = BLACKLIST_KEY_PREFIX + email;
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }
}