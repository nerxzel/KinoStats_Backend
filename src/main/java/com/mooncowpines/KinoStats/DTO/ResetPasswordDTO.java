package com.mooncowpines.KinoStats.DTO;

import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(
    String email,
    String code,
    @Size(min = 7, message = "Password must be at least 7 characters")
    String newPassword
) {}