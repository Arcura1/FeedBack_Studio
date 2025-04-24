package org.example.feedbackstudio.login.authority.model.query;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.authority.authorityenum.AuthorityType;
import org.example.feedbackstudio.login.authority.authorityenum.EffectTypeEnum;

@Getter
@Setter
public class AuthorityQueryModel {
    private String name;
    private AuthorityType authorityType;
    private EffectTypeEnum effectTypeEnum;
    private Long classroomId;
    private Long organizationId;
}
