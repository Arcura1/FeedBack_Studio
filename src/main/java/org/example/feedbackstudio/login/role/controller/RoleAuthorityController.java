package org.example.feedbackstudio.login.role.controller;

import org.example.feedbackstudio.login.role.entity.roleAuthorityEntitiy;
import org.example.feedbackstudio.login.role.model.RoleAuthorityRequestModel;
import org.example.feedbackstudio.login.role.service.RoleAuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role-authorities")
public class RoleAuthorityController {

    private final RoleAuthorityService roleAuthorityService;

    public RoleAuthorityController(RoleAuthorityService roleAuthorityService) {
        this.roleAuthorityService = roleAuthorityService;
    }

    // 📌 Create - Yeni Role-Authority Bağlantısı Ekle
    @PostMapping
    public ResponseEntity<roleAuthorityEntitiy> createRoleAuthority(@RequestBody RoleAuthorityRequestModel model) {
        roleAuthorityEntitiy savedEntity = roleAuthorityService.saveRoleAuthority(model);
        return ResponseEntity.ok(savedEntity);
    }

    // 📌 Read - Tüm Role-Authority Bağlantılarını Getir
    @GetMapping
    public ResponseEntity<List<roleAuthorityEntitiy>> getAllRoleAuthorities() {
        List<roleAuthorityEntitiy> list = roleAuthorityService.getAllRoleAuthorities();
        return ResponseEntity.ok(list);
    }

    // 📌 Read - ID'ye Göre Role-Authority Bağlantısını Getir
    @GetMapping("/{id}")
    public ResponseEntity<roleAuthorityEntitiy> getRoleAuthorityById(@PathVariable Long id) {
        Optional<roleAuthorityEntitiy> entity = roleAuthorityService.getRoleAuthorityById(id);
        return entity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 📌 Update - Role-Authority Bağlantısını Güncelle
    @PutMapping("/{id}")
    public ResponseEntity<roleAuthorityEntitiy> updateRoleAuthority(@PathVariable Long id,
                                                                   @RequestBody RoleAuthorityRequestModel model) {
        roleAuthorityEntitiy updatedEntity = roleAuthorityService.updateRoleAuthority(id, model);
        return ResponseEntity.ok(updatedEntity);
    }

    // 📌 Delete - Role-Authority Bağlantısını Sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleAuthority(@PathVariable Long id) {
        roleAuthorityService.deleteRoleAuthority(id);
        return ResponseEntity.noContent().build();
    }
}
