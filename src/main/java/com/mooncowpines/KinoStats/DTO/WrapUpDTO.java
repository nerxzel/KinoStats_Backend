package com.mooncowpines.KinoStats.DTO;

import java.util.List;

import com.mooncowpines.KinoStats.Repository.Projections.TypeWatches;

public record WrapUpDTO(
    Integer moviesWatched,
    Integer minutesWatched,
    MovieCardDTO firstWatched,
    MovieCardDTO lastWatched,
    List<TypeWatches> moviesWatchedByGenre,
    List<TypeWatches> topDirectors,
    List<TypeWatches> topActors
) {

}
