package com.mooncowpines.KinoStats.DTO;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public record LoginResponse(
    Long userId,
    String username,
    String email,
    Collection<? extends GrantedAuthority> authorities
) {}