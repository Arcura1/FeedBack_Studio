package org.example.feedbackstudio.login.user.service;

import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.example.feedbackstudio.login.user.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecifications {

    public static Specification<User> hasId(Long id) {
        return (root, query, cb) -> id == null ? null : cb.equal(root.get("id"), id);
    }

    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, cb) -> firstName == null ? null :
                cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<User> hasLastName(String lastName) {
        return (root, query, cb) -> lastName == null ? null :
                cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, cb) -> email == null ? null :
                cb.equal(cb.lower(root.get("email")), email.toLowerCase());
    }

    public static Specification<User> hasPhone(String phone) {
        return (root, query, cb) -> phone == null ? null :
                cb.equal(root.get("phone"), phone);
    }

    public static Specification<User> hasPassword(String password) {
        if (StringUtils.hasText(password)) {
            System.err.println("UserSpecifications.hasPassword: Şifreye göre arama güvenlik nedeniyle desteklenmiyor ve yoksayılıyor.");
        }
        return (root, query, cb) -> null; // Her zaman true (etkisiz) bir koşul döndür
    }

    public static Specification<User> hasRole(String role) {
        return (root, query, cb) -> role == null ? null :
                cb.equal(root.get("role"), role);
    }

    public static Specification<User> hasRoleId(Long roleId) {
        return (root, query, cb) -> roleId == null ? null :
                cb.equal(root.get("roleId"), roleId);
    }

    public static Specification<User> hasRoleType(RoleTypeEnum roleType) {
        return (root, query, cb) -> {
            if (roleType == null) return null;
            return cb.equal(root.join("roleEntity").get("roleTypeEnum"), roleType);
        };
    }
}
