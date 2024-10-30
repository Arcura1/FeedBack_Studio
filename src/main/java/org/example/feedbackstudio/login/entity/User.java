package org.example.feedbackstudio.login.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String firstName; // Ad
    private String lastName; // Soyad
    private String email;
    private String phone; // Telefon numarası
    private String password;

    // Constructor
    public User() {}

    public User(String firstName, String lastName, String email, String phone, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

}
