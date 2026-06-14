package com.mooncowpines.KinoStats.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mooncowpines.KinoStats.DTO.MovieCardDTO;
import com.mooncowpines.KinoStats.DTO.MovieListAddDTO;
import com.mooncowpines.KinoStats.DTO.MovieListDTO;
import com.mooncowpines.KinoStats.DTO.MovieListRequestDTO;
import com.mooncowpines.KinoStats.Model.Film;
import com.mooncowpines.KinoStats.Model.MovieList;
import com.mooncowpines.KinoStats.Model.User;
import com.mooncowpines.KinoStats.Repository.MovieListRepository;
import com.mooncowpines.KinoStats.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieListService {

    private final MovieListRepository movieListRepository;
    private final UserRepository userRepository;
    private final FilmService filmService;

    public void createWatchlist(User user) {
        MovieList watchlist = new MovieList();
        watchlist.setName("Watchlist");
        watchlist.setIsWatchlist(true);
        watchlist.setUser(user);
        movieListRepository.save(watchlist);
    }

    public MovieListDTO createList(MovieListRequestDTO request) {
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        MovieList list = new MovieList();
        list.setName(request.name());
        list.setIsWatchlist(false);
        list.setUser(user);
        list = movieListRepository.save(list);

        return toDTO(list);
    }

    public List<MovieListDTO> getListsByUser(Long userId) {
        return movieListRepository.findByUserId(userId).stream()
            .map(this::toDTO)
            .toList();
    }

    public MovieListDTO getList(Long listId) {
        MovieList list = movieListRepository.findById(listId)
            .orElseThrow(() -> new EntityNotFoundException("List not found"));
        return toDTO(list);
    }

    public void addFilmToList(MovieListAddDTO request) {
        MovieList list = movieListRepository.findById(request.movieListId())
            .orElseThrow(() -> new EntityNotFoundException("List not found"));

        Film film = filmService.findOrFetchAndSave(request.filmId());
        list.getFilms().add(film);
        movieListRepository.save(list);
    }

    public void removeFilmFromList(Long listId, Long filmId) {
        MovieList list = movieListRepository.findById(listId)
            .orElseThrow(() -> new EntityNotFoundException("List not found"));

        list.getFilms().removeIf(f -> f.getId().equals(filmId));
        movieListRepository.save(list);
    }

    public void deleteList(Long listId) {
        if (!movieListRepository.existsById(listId)) {
            throw new EntityNotFoundException("List not found");
        }
        movieListRepository.deleteById(listId);
    }

    public Optional<MovieList> getWatchList(Long userId){
        return movieListRepository.findByUserIdAndIsWatchlistTrue(userId);
    }

    private MovieListDTO toDTO(MovieList list) {
        List<MovieCardDTO> movies = list.getFilms().stream()
            .map(f -> new MovieCardDTO(f.getId(), f.getTitle(), f.getPosterPath(), f.getReleaseYear(), f.getLengthInMinutes()))
            .toList();

        return new MovieListDTO(
            list.getId(),
            list.getName(),
            movies.size(),
            movies,
            list.getIsWatchlist()
        );
    }

    public MovieListDTO updateList(Long listId, MovieListRequestDTO request) {
        MovieList list = movieListRepository.findById(listId)
            .orElseThrow(() -> new EntityNotFoundException("List not found"));
        list.setName(request.name());
        list = movieListRepository.save(list);
        return toDTO(list);
    }
}
