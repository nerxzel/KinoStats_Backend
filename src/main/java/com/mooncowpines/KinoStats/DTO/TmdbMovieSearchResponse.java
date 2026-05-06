package com.mooncowpines.KinoStats.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbMovieSearchResponse(
    Long id,
    String title,
    @JsonProperty("release_date") String releaseDate,
    @JsonProperty("poster_path") String posterPath
) {
    
}
