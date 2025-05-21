package org.example.feedbackstudio.login.role.controller;

import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.model.RoleQueryRequest;
import org.example.feedbackstudio.login.role.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // 📌 Create - Yeni Rol Ekle
    @PostMapping
    public ResponseEntity<roleEntity> createRole(@RequestBody roleEntity role) {
        roleEntity savedRole = roleService.saveRole(role);
        return ResponseEntity.ok(savedRole);
    }

    // 📌 Read - Tüm Rolleri Getir
    @GetMapping
    public ResponseEntity<List<roleEntity>> getAllRoles() {
        List<roleEntity> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // 📌 Read - ID'ye Göre Rol Getir
    @GetMapping("/{id}")
    public ResponseEntity<roleEntity> getRoleById(@PathVariable Long id) {
        Optional<roleEntity> role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/{roleType}")
    public ResponseEntity<List<roleEntity>> getRolesByRoleType(@PathVariable String roleType) {
        Optional<List<roleEntity>> roles = roleService.getRolesByRoleType(roleType);
        return roles.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/query")
    public ResponseEntity<List<roleEntity>> queryRoles(@RequestBody RoleQueryRequest request) {
        List<roleEntity> roles = roleService.queryRoles(request);
        return ResponseEntity.ok(roles);
    }
    // 📌 Update - Rol Güncelle
    @PutMapping("/{id}")
    public ResponseEntity<roleEntity> updateRole(@PathVariable Long id, @RequestBody roleEntity role) {
        roleEntity updatedRole = roleService.updateRole(id, role);
        return ResponseEntity.ok(updatedRole);
    }

    // 📌 Delete - Rol Sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
