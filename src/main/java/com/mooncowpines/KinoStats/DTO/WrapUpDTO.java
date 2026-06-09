package com.mooncowpines.KinoStats.DTO;

import java.util.List;

import com.mooncowpines.KinoStats.Repository.Projections.TypeWatches;

public record WrapUpDTO(
    Integer moviesWatched,
    Integer minutesWatched,
    TypeWatches mostWatchedGenre,
    TypeWatches mostWatchedCountry,
    TypeWatches mostWatchedActor,
    TypeWatches mostWatchedDirector,
    MovieCardDTO firstWatched,
    MovieCardDTO lastWatched,
    float averageRating,
    List<MovieCardDTO> topMovies
    // List<TypeWatches> moviesWatchedByGenre,
    // List<TypeWatches> moviesWatchedByCountry,
    // List<TypeWatches> topDirectors,
    // List<TypeWatches> topActors,
    // List<RatingsCount> ratingsCount,
    // List<DecadeWatches> moviesWatchedByDecade
) {

}
