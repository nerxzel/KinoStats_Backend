package com.mooncowpines.KinoStats.DTO;

public record StatsRequestDTO(
    Long userId,
    Integer month,
    Integer year
) {
    
}
