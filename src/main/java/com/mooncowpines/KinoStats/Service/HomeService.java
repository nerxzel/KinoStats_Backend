package com.mooncowpines.KinoStats.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mooncowpines.KinoStats.DTO.HomeDTO;
import com.mooncowpines.KinoStats.DTO.MovieCardDTO;
import com.mooncowpines.KinoStats.Model.Film;
import com.mooncowpines.KinoStats.Model.Log;
import com.mooncowpines.KinoStats.Model.MovieList;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
    
    private final MovieListService movieListService;
    private final LogService logService;

    public HomeDTO getHomeData(Long userId){
        Optional<MovieList> watchList = movieListService.getWatchList(userId);
        List<Log> latestLogs = logService.findLatestLogs(userId);
        MovieCardDTO lastSeen;
        List<MovieCardDTO> lastSeenCards;
        List<MovieCardDTO> watchListCards;
        
        if(!latestLogs.isEmpty()){
            lastSeen = MovieCardDTO.fromFilm(latestLogs.get(0).getFilm());
            List<Film> lastSeenFilms = latestLogs.stream().map(Log::getFilm).toList();
            lastSeenCards = lastSeenFilms.stream().map(MovieCardDTO::fromFilm).toList();
        } else {
            lastSeen = null;
            lastSeenCards = null;
        }

        if (watchList.isPresent()){
            watchListCards = watchList.get().getFilms().stream().limit(6).map(MovieCardDTO::fromFilm).toList();
        } else {
            watchListCards = null;
        }
        return new HomeDTO(watchListCards, lastSeen, lastSeenCards);
    }
}
