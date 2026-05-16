package com.mooncowpines.KinoStats.DTO;

import com.mooncowpines.KinoStats.Model.Film;

public record MovieCardDTO(
    Long id,
    String name,
    String path,
    Integer yearOfRelease,
    Integer duration
) {
    public static MovieCardDTO fromFilm(Film film) {
        return new MovieCardDTO(
            film.getId(),
            film.getTitle(),
            film.getPosterPath(),
            film.getReleaseYear(),
            film.getLengthInMinutes()
        );
    }
}
