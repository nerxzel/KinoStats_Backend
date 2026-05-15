package com.mooncowpines.KinoStats.DTO;


public record MovieDetailsDTO (
    Long id,
    String title,
    Integer runtime,
    String releaseDate,
    String productionCountries,
    String genres,
    String backdropPath,
    String posterPath,
    String overview,
    String director,
    String actors,
    String productionCompany
){    

}

