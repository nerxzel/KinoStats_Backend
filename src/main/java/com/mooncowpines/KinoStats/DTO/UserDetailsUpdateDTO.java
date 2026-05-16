package com.mooncowpines.KinoStats.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserDetailsUpdateDTO(

    @Email(message = "Invalid Email")
    String email,
    
    @Size(min = 3, max = 14, message = "Username must be at least 3 characters and less than 15")
    String username,

    @Size(min = 7, message = "Password must be at least 7 characters")
    String password
) {
} 
