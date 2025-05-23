package org.example.feedbackstudio.login.role.model;

import lombok.Data;

@Data
public class RoleQueryRequest {
    private String name;
    private String description;
    private String roleTypeEnum;
    private Long organizationId;
}
