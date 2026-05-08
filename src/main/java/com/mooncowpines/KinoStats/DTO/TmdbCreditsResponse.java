package com.mooncowpines.KinoStats.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbCreditsResponse(
    List<CastMember> cast,
    List<CrewMember> crew
) {
        @JsonIgnoreProperties(ignoreUnknown = true)
    public record CastMember(
        Long id,
        String name,
        @JsonProperty("profile_path") String profilePath,
        int order
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CrewMember(
        Long id,
        String name,
        @JsonProperty("profile_path") String profilePath,
        String job
    ) {}
}
