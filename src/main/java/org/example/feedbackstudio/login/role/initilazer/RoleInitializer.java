package org.example.feedbackstudio.login.role.initilazer;

import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.repository.RoleRepository;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        createRoleIfNotExists(RoleTypeEnum.GUEST, "Guest Role");
        createRoleIfNotExists(RoleTypeEnum.ADMIN, "Admin Role");
        createRoleIfNotExists(RoleTypeEnum.TEACHER, "TEACHER Role");
        createRoleIfNotExists(RoleTypeEnum.STUDENT, "STUDENT Role");
        createRoleIfNotExists(RoleTypeEnum.EXECUTIVE, "EXECUTIVE Role");
    }

    private void createRoleIfNotExists(RoleTypeEnum roleType, String description) {
        if (!roleRepository.existsByRoleTypeEnum(roleType)) {
            roleEntity role = roleEntity.builder()
                    .name(roleType.name()) // Enum adını kullanarak isim atama
                    .description(description)
                    .roleTypeEnum(roleType)
                    .build();
            roleRepository.save(role);
            System.out.println(roleType.name() + " rolü eklendi.");
        } else {
            System.out.println(roleType.name() + " rolü zaten var.");
        }
    }
}
