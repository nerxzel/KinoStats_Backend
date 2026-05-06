package com.mooncowpines.KinoStats.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TmdbMovieSearchResponseWrapper(
    List<TmdbMovieSearchResponse> results
) {
}
