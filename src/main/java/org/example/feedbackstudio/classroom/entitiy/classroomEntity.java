package org.example.feedbackstudio.classroom.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.feedbackstudio.organization.entity.organizationEntity;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class classroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sınıf veya derslik için tanımlayıcı isim
    @Column(nullable = false, unique = true)
    private String name;

    // Bulunduğu kat
    @Column(nullable = false)
    private Integer floor;

    // Oda numarası
    @Column(nullable = false)
    private String roomNumber;

    // Odanın kapasitesi (kaç kişi alabilir)
    @Column(nullable = false)
    private Integer capacity;

    // Projeksiyon cihazı var mı?
    @Column(nullable = false)
    private Boolean hasProjector;

    // Yazı tahtası var mı?
    @Column(nullable = false)
    private Boolean hasWhiteboard;

    // Klima veya havalandırma sistemi var mı?
    @Column(nullable = false)
    private Boolean hasAirConditioning;

    // Derslik ile ilgili ek açıklamalar
    @Column(length = 500)
    private String description;

    // Organization entity ile ilişki
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private organizationEntity organization;
}
