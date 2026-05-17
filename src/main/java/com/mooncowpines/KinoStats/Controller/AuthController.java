package com.mooncowpines.KinoStats.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooncowpines.KinoStats.DTO.LoginResponse;
import com.mooncowpines.KinoStats.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return userService.getUserByName(authentication.getName())
                .map(user -> ResponseEntity.ok(new LoginResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        authentication.getAuthorities())))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
