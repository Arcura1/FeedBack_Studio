package org.example.feedbackstudio.login.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private Long roleId;
}
