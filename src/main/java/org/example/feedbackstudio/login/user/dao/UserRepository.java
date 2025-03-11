package org.example.feedbackstudio.login.user.dao;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.example.feedbackstudio.login.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods if needed

    User findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roleEntity r WHERE r.roleTypeEnum = :roleType")
    List<User> findByRoleName(@Param("roleType") RoleTypeEnum roleType);
}
