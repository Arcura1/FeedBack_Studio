package org.example.feedbackstudio.login.user.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private Boolean create;
    private Long organizationId;
//    private boolean read;
//    private boolean update;
//    private boolean delete;
}
