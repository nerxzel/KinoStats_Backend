package com.mooncowpines.KinoStats.DTO;

import java.time.LocalDate;

import com.mooncowpines.KinoStats.Model.Log;

public record LogDTO(
    Long id,
    LocalDate date,
    Float rating,
    String review,
    Long filmId,
    Long userId,
    String posterPath,
    Integer releaseYear,
    String title) {
            
    public static LogDTO logToDto(Log log) {
        return new LogDTO(log.getId(),
            log.getDate(),
            log.getRating(),
            log.getReview(),
            log.getFilm().getId(),
            log.getUser().getId(),
            log.getFilm().getPosterPath(),
            log.getFilm().getReleaseYear(),
            log.getFilm().getTitle());
    }
}
