package com.mooncowpines.KinoStats.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mooncowpines.KinoStats.Model.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long>{
    Optional<PasswordReset> findByUserIdAndCodeAndUsedFalse(Long userId, String code);
}
