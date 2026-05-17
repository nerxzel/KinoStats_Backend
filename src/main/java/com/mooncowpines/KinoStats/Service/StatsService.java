package com.mooncowpines.KinoStats.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mooncowpines.KinoStats.DTO.StatsRequestDTO;
import com.mooncowpines.KinoStats.DTO.StatsResponseDTO;
import com.mooncowpines.KinoStats.Repository.LogRepository;
import com.mooncowpines.KinoStats.Repository.Projections.DecadeWatches;
import com.mooncowpines.KinoStats.Repository.Projections.RatingsCount;
import com.mooncowpines.KinoStats.Repository.Projections.TypeWatches;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsService {
    
    private final LogRepository logRepository;

    public StatsResponseDTO calculateStats(StatsRequestDTO request){
        LocalDate starDate;
        LocalDate endDate;

        if (request.month() == null){
            starDate = LocalDate.of(request.year(), 1, 1);
            endDate = LocalDate.of(request.year(), 12, 31);
        } else {
            starDate = LocalDate.of(request.year(), request.month(), 1);
            endDate = starDate.with(TemporalAdjusters.lastDayOfMonth());
        }

        Integer moviesWatched = logRepository.countMoviesWatched(request.userId(), starDate, endDate);
        Integer watchTimeMinutes = logRepository.sumWatchTime(request.userId(), starDate, endDate);
        Integer watchTimeHours;
        if (watchTimeMinutes != null){
            watchTimeHours = Math.round(watchTimeMinutes/60);
        } else {
            watchTimeHours = 0;
        }
        List<TypeWatches> genreWatches = logRepository.watchesByGenre(request.userId(), starDate, endDate);
        List<TypeWatches> countryWatches = logRepository.watchesByCountry(request.userId(), starDate, endDate);
        List<TypeWatches> directorWatches = logRepository.watchesByPerson(request.userId(), 1L, starDate, endDate).stream().limit(5).toList();
        List<TypeWatches> actorWatches = logRepository.watchesByPerson(request.userId(), 2L, starDate, endDate).stream().limit(5).toList();
        List<RatingsCount> ratingsCounts = logRepository.countsByRating(request.userId(), starDate, endDate);
        List<DecadeWatches> decadeWatches = logRepository.watchesByDecade(request.userId(), starDate, endDate);

        return new StatsResponseDTO(moviesWatched, watchTimeMinutes, watchTimeHours, genreWatches, countryWatches, directorWatches, actorWatches, ratingsCounts, decadeWatches);
    }
}
