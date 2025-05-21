package org.example.feedbackstudio.login.authority.model.query;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.authority.authorityenum.AuthorityType;
import org.example.feedbackstudio.login.authority.authorityenum.EffectTypeEnum;

@Getter
@Setter
public class AuthorityQueryDTO {
    private Long organizationId;
    private Long classroomId;
    private Long classroomUserId;
    private Long pdfInfoId;
    private Long homewrokId;
    private AuthorityType authorityType;
    private EffectTypeEnum effectTypeEnum;
}
