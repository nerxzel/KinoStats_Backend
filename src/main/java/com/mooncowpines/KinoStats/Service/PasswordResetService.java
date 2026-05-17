package com.mooncowpines.KinoStats.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mooncowpines.KinoStats.Exceptions.ValidationException;
import com.mooncowpines.KinoStats.Model.PasswordReset;
import com.mooncowpines.KinoStats.Model.User;
import com.mooncowpines.KinoStats.Repository.PasswordResetRepository;
import com.mooncowpines.KinoStats.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetRepository resetRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public void requestReset(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String code = String.format("%06d", new SecureRandom().nextInt(999999));

        PasswordReset reset = new PasswordReset();
        reset.setUser(user);
        reset.setCode(code);
        reset.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        resetRepository.save(reset);

        emailService.sendResetCode(email, code);
    }

    public void resetPassword(String email, String code, String newPassword) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        PasswordReset reset = resetRepository
            .findByUserIdAndCodeAndUsedFalse(user.getId(), code)
            .orElseThrow(() -> new ValidationException("Invalid code"));

        if (reset.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Code expired");
        }

        reset.setUsed(true);
        resetRepository.save(reset);

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}