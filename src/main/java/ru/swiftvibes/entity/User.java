package ru.swiftvibes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;



/**
 * Created by Victor 25.09.2025
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor // for jakarta
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    public User(String name, String email, int age, LocalDate createdAt) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }
}
