package com.mooncowpines.KinoStats.DTO;

import java.util.List;

import com.mooncowpines.KinoStats.Repository.Projections.DecadeWatches;
import com.mooncowpines.KinoStats.Repository.Projections.RatingsCount;
import com.mooncowpines.KinoStats.Repository.Projections.TypeWatches;

public record StatsResponseDTO(
    Integer moviesWatched,
    Integer minutesWatched,
    Integer hoursWatched,
    List<TypeWatches> moviesWatchedByGenre,
    List<TypeWatches> moviesWatchedByCountry,
    List<TypeWatches> topDirectors,
    List<TypeWatches> topActors,
    List<RatingsCount> ratingsCount,
    List<DecadeWatches> moviesWatchedByDecade
) {

}
