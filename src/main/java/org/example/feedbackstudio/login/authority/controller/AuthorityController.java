package org.example.feedbackstudio.login.authority.controller;

import org.example.feedbackstudio.login.authority.entity.Authority;
import org.example.feedbackstudio.login.authority.model.query.AuthorityQueryDTO;
import org.example.feedbackstudio.login.authority.model.query.AuthorityQueryModel;
import org.example.feedbackstudio.login.authority.service.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    // 📌 Create - Yeni Yetki Ekleme
    @PostMapping
    public ResponseEntity<Authority> createAuthority(@RequestBody Authority authority) {
        Authority savedAuthority = authorityService.saveAuthority(authority);
        return ResponseEntity.ok(savedAuthority);
    }

    // 📌 Read - Tüm Yetkileri Getir
    @GetMapping
    public ResponseEntity<List<Authority>> getAllAuthorities() {
        List<Authority> authorities = authorityService.getAllAuthorities();
        return ResponseEntity.ok(authorities);
    }

    // 📌 Read - ID'ye Göre Yetki Getir
    @GetMapping("/{id}")
    public ResponseEntity<Authority> getAuthorityById(@PathVariable Long id) {
        Optional<Authority> authority = authorityService.getAuthorityById(id);
        return authority.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/getByRole/{id}")
    public ResponseEntity<List<Authority>> getAuthoritiesByRoleId(@PathVariable Long id) {
        List<Authority> authorities = authorityService.getAuthByRole(id);
        if (authorities.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(authorities);
    }

    // 📌 Update - Yetki Güncelle
    @PutMapping("/{id}")
    public ResponseEntity<Authority> updateAuthority(@PathVariable Long id, @RequestBody Authority authority) {
        Authority updatedAuthority = authorityService.updateAuthority(id, authority);
        return ResponseEntity.ok(updatedAuthority);
    }

    // 📌 Delete - Yetki Sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthority(@PathVariable Long id) {
        authorityService.deleteAuthority(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/query")
    public ResponseEntity<List<Authority>> queryAuthorities(@RequestBody AuthorityQueryModel queryModel) {
        List<Authority> results = authorityService.queryAuthorities(queryModel);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Authority>> queryAuthorities(@RequestBody AuthorityQueryDTO queryModel) {
        List<Authority> results = authorityService.searchAuthorities(queryModel);
        return ResponseEntity.ok(results);
    }


}
