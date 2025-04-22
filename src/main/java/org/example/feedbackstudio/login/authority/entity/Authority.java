package org.example.feedbackstudio.login.authority.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.classroom.entitiy.ClassroomUserEntity;
import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.example.feedbackstudio.homework.entitiy.HomeworkEntity;
import org.example.feedbackstudio.login.authority.authorityenum.AuthorityType;
import org.example.feedbackstudio.login.authority.authorityenum.EffectTypeEnum;
import org.example.feedbackstudio.note.pdfInfo.entitiy.PdfInfoEntity;
import org.example.feedbackstudio.organization.entity.organizationEntity;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Yetki adı (örn: ROLE_ADMIN, ROLE_USER)

    @Column(length = 255)
    private String description; // Yetkinin açıklaması (isteğe bağlı)

    @Enumerated(EnumType.STRING)  // Enum'ı String olarak saklar (örn: "ADD_HOMEWORK")
    @Column(nullable = false)
    private AuthorityType authorityType;

    @Enumerated(EnumType.STRING)  // Enum'ı String olarak saklar (örn: "ADD_HOMEWORK")
    @Column(nullable = true)
    private EffectTypeEnum effectTypeEnum;

    // Organization ID doğrudan tutulacak
    @Column(name = "classroom_id", insertable = true, updatable = false)
    private Long classroomId;

    // Organization entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_id", insertable = false, updatable = false)
    private classroomEntity classroom;

    // Organization ID doğrudan tutulacak
    @Column(name = "organization_id", insertable = true, updatable = false)
    private Long organizationId;

    // Organization entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    private organizationEntity organization;


    // Organization ID doğrudan tutulacak
    @Column(name = "classroomUser_id", insertable = true, updatable = false)
    private Long classroomUserId;

    // Organization entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroomUser_id", insertable = false, updatable = false)
    private ClassroomUserEntity classroomUser;

    // Organization ID doğrudan tutulacak
    @Column(name = "pdfInfo_id", insertable = true, updatable = false)
    private Long pdfInfoId;

    // Organization entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pdfInfo_id", insertable = false, updatable = false)
    private PdfInfoEntity pdfInfo;


    // Organization ID doğrudan tutulacak
    @Column(name = "homewrok_id", insertable = true, updatable = false)
    private Long homewrokId;

    // Organization entity ile ilişki
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "homewrok_id", insertable = false, updatable = false)
    private HomeworkEntity homework;
}
