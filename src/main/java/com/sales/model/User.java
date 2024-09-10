package com.sales.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Unique constraint
    private String email;

    @Column(nullable = false)
    private String password;

    // Getters and Setters
}
