package org.example.feedbackstudio.login.user.model;

import lombok.Data;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;

@Data
public class UserQueryModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String role;
    private Long roleId;
    private RoleTypeEnum roleType;
}
