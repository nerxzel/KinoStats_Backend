package com.mooncowpines.KinoStats.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooncowpines.KinoStats.DTO.ForgotPasswordDTO;
import com.mooncowpines.KinoStats.DTO.ResetPasswordDTO;
import com.mooncowpines.KinoStats.Service.EmailService;
import com.mooncowpines.KinoStats.Service.PasswordResetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService resetService;
    private final EmailService emailService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> requestReset(@RequestBody ForgotPasswordDTO dto) {
        resetService.requestReset(dto.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        resetService.resetPassword(dto.email(), dto.code(), dto.newPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test-email")
    public ResponseEntity<Void> test() {
        emailService.sendResetCode("alfo.luna.a@gmail.com", "123456");
        return ResponseEntity.ok().build();
    }
}