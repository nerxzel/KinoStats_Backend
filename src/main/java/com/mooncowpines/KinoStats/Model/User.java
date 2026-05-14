package com.mooncowpines.KinoStats.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 14, message = "Username must be at least 3 characters and less than 15")
    private String username;

    @Column(nullable = false)
    @Size(min = 7, message = "Password must be at least 7 characters")
    private String password;

    @Column(unique = true, nullable = false)
    @Email(message = "Invalid Email")
    private String email;
}