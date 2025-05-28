package org.example.feedbackstudio.login.user.service;

import org.example.feedbackstudio.login.role.repository.RoleRepository;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.example.feedbackstudio.login.user.dao.UserRepository;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.login.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Kullanıcıyı kaydeder, aynı email ve şifreye sahip kullanıcı varsa kara listeye ekler.
     *
     * @param user Kaydedilecek kullanıcı
     * @return Kaydedilen kullanıcı
     */
    public User saveUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            addToBlacklist(user.getEmail());
            throw new IllegalArgumentException("Bu e-posta ve şifre zaten kullanılıyor, kullanıcı kara listeye alındı.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // şifreyi hashle

        if (user.getRoleId() == null && Objects.equals(user.getRole(), "GUEST")) {
            user.setRoleId(roleRepository.findByRoleTypeEnumAndOrganizationId(RoleTypeEnum.valueOf(user.getRole()), null).getId());
        }

        return userRepository.save(user);
    }


    /**
     * Kullanıcıyı ID'ye göre getirir.
     *
     * @param id Kullanıcı ID
     * @return Kullanıcı opsiyonu
     */
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
                        user.getRole(),
                        null,
                        user.getRoleEntity().getOrganizationId()
                ))
                .collect(Collectors.toList());

        return Optional.of(userDTOs);
    }

    /**
     * E-posta ile kullanıcı getirir.
     *
     * @param email E-posta adresi
     * @return Kullanıcı nesnesi
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Kullanıcıyı ID'ye göre siler.
     *
     * @param id Kullanıcı ID
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    /**
     * Kullanıcı girişini doğrular.
     *
     * @param email    Kullanıcı email
     * @param password Kullanıcı şifre
     * @return Kullanıcı nesnesi
     */
    public UserDTO login(String email, String password) {
        if (isEmailBlacklisted(email)) {
            throw new IllegalArgumentException("Bu e-posta kara listede.");
        }

        User user = getUserByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            UserDTO u = new UserDTO();
            u.setId(user.getId());
            u.setEmail(user.getEmail());
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setCreate(null);
            u.setOrganizationId(user.getRoleEntity().getOrganizationId());
            u.setRole(roleRepository.findById(user.getRoleId()).get().getRoleTypeEnum().toString());
            return u;
        }
        return null;
    }


    /**
     * E-posta adresini Redis üzerinden kara listeye ekler.
     *
     * @param email E-posta adresi
     */
    public void addToBlacklist(String email) {
        String key = BLACKLIST_KEY_PREFIX + email;
        redisTemplate.opsForValue().set(key, "BLACKLISTED", 7, TimeUnit.DAYS); // 7 gün geçerli
        System.out.println("E-posta kara listeye eklendi: " + email);
    }

    /**
     * E-postanın kara listede olup olmadığını kontrol eder.
     *
     * @param email E-posta adresi
     * @return boolean Kara listede mi?
     */
    public boolean isEmailBlacklisted(String email) {
        String key = BLACKLIST_KEY_PREFIX + email;
        return redisTemplate.hasKey(key);
    }
}
