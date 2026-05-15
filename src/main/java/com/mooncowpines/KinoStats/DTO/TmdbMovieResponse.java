package com.mooncowpines.KinoStats.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TmdbMovieResponse (
    Long id,
    String title,
    Integer runtime,
    @JsonProperty("release_date") String releaseDate,
    @JsonProperty("production_countries") List<ProductionCountry> productionCountries,
    @JsonProperty("genres") List<Genre> genres,
    @JsonProperty("backdrop_path") String backdropPath,
    @JsonProperty("poster_path") String posterPath,
    String overview,
    @JsonProperty("production_companies") List<ProductionCompany> productionCompanies
){
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProductionCountry(
        @JsonProperty("iso_3166_1") String iso,
        String name
    ) {}

    public record Genre(
        @JsonProperty("id") Long id,
        String name
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProductionCompany(
        String name
    ) {}
}
