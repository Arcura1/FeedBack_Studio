package org.example.feedbackstudio.login.role.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class roleEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    // Sınıf veya derslik için tanımlayıcı isim
//    @Column(nullable = false, unique = true)
//    private String name;
//
//    // Bulunduğu kat
//    @Column(nullable = false)
//    private Integer floor;


}
