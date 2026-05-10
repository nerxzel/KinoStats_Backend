package com.mooncowpines.KinoStats.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mooncowpines.KinoStats.DTO.LogDTO;
import com.mooncowpines.KinoStats.DTO.LogRequestDTO;
import com.mooncowpines.KinoStats.DTO.TmdbCreditsResponse;
import com.mooncowpines.KinoStats.DTO.TmdbMovieResponse;
import com.mooncowpines.KinoStats.Model.Country;
import com.mooncowpines.KinoStats.Model.Film;
import com.mooncowpines.KinoStats.Model.Genre;
import com.mooncowpines.KinoStats.Model.Job;
import com.mooncowpines.KinoStats.Model.Log;
import com.mooncowpines.KinoStats.Model.MovieRole;
import com.mooncowpines.KinoStats.Model.Person;
import com.mooncowpines.KinoStats.Model.User;
import com.mooncowpines.KinoStats.Repository.CountryRepository;
import com.mooncowpines.KinoStats.Repository.FilmRepository;
import com.mooncowpines.KinoStats.Repository.GenreRepository;
import com.mooncowpines.KinoStats.Repository.JobRepository;
import com.mooncowpines.KinoStats.Repository.LogRepository;
import com.mooncowpines.KinoStats.Repository.MovieRoleRepository;
import com.mooncowpines.KinoStats.Repository.PersonRepository;
import com.mooncowpines.KinoStats.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final TmdbService tmdbService;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;
    private final JobRepository jobRepository;
    private final PersonRepository personRepository;
    private final MovieRoleRepository movieRoleRepository;

    public List<Log> getLogs() {
        return logRepository.findAll();
    }

    public List<LogDTO> getLogsByUserId(Long id) {
        List<Log> logs = logRepository.findByUserId(id);
        List<LogDTO> logsDTDtos = logs.stream()
                .map(LogDTO::logToDto)
                .toList();
        return logsDTDtos;
    }

    public Optional<Log> getLogById(Long id) {
        return logRepository.findById(id);
    }

    public void addLog(LogRequestDTO logRequest) {
        User user = userRepository.findById(logRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Film film = filmRepository.findById(logRequest.getFilmId())
                .orElseGet(() -> fetchAndSaveFromTmdb(logRequest.getFilmId()));

        saveCredits(film);
        
        Log log = new Log();
        log.setDate(logRequest.getDate());
        log.setReview(logRequest.getReview());
        log.setRating(logRequest.getRating());
        log.setFilm(film);
        log.setUser(user);
        log.setFirstWatch(logRequest.getFirstWatch());

        logRepository.save(log);
    }

    private Film fetchAndSaveFromTmdb(Long tmdbId) {
        TmdbMovieResponse response = tmdbService.fetchMovie(tmdbId);

        Film film = new Film();
        film.setId(response.id());
        film.setTitle(response.title());
        film.setDateAddedToDB(LocalDate.now());
        film.setPosterPath(response.posterPath());

        if (response.releaseDate() != null && !response.releaseDate().isEmpty()) {
            film.setReleaseYear(LocalDate.parse(response.releaseDate()).getYear());
        }

        if (response.runtime() != null) {
            film.setLengthInMinutes(response.runtime());
        }

        if (response.productionCountries() != null) {
            Set<Country> countries = response.productionCountries().stream()
                    .map(pc -> countryRepository.findById(pc.iso()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            film.setCountries(countries);
        }

        if (response.genres() != null) {
            Set<Genre> genres = response.genres().stream()
                    .map(g -> genreRepository.findById(g.id()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            film.setGenres(genres);
        }

        return filmRepository.save(film);
    }

    public void saveCredits(Film film){
        TmdbCreditsResponse response = tmdbService.fetchMovieCredits(film.getId());

        Job jobActor = jobRepository.findById(2L).get(); //harcoded pero los trabajos planeo insertarlos manualmente asi que esta bbien

        response.cast().stream()
            .limit(9)
            .forEach(member -> {
                Optional<Person> personaOpt = personRepository.findById(member.id());
                Person persona;
                if (!personaOpt.isPresent()){
                    persona = new Person();
                    persona.setId(member.id());
                    persona.setName(member.name());
                    persona.setProfilePath(member.profilePath());
                    personRepository.save(persona);
                } else {
                    persona = personaOpt.get();
                }

                MovieRole newMovieRole = new MovieRole();
                newMovieRole.setFilm(film);
                newMovieRole.setJob(jobActor);
                newMovieRole.setPerson(persona);
                movieRoleRepository.save(newMovieRole);
            });

        Job jobDirector = jobRepository.findById(1L).get();
        response.crew().stream()
            .filter(member -> member.job().equals(jobDirector.getName()))
            .forEach(member -> {
                Optional<Person> personaOpt = personRepository.findById(member.id());
                Person persona;
                if (!personaOpt.isPresent()){
                    persona = new Person();
                    persona.setId(member.id());
                    persona.setName(member.name());
                    persona.setProfilePath(member.profilePath());
                    personRepository.save(persona);
                } else {
                    persona = personaOpt.get();
                }

                MovieRole newMovieRole = new MovieRole();
                newMovieRole.setFilm(film);
                newMovieRole.setJob(jobActor);
                newMovieRole.setPerson(persona);
                movieRoleRepository.save(newMovieRole);
            });
            
    }

    public void updateLog(Log log) {
        logRepository.save(log);
    }

    public Log updateLog(Long id, LogRequestDTO request) {
        Log log = getLogById(id).orElseThrow();
        log.setDate(request.getDate());
        log.setReview(request.getReview());
        log.setRating(request.getRating());
        log.setFirstWatch(request.getFirstWatch());
        return logRepository.save(log);
    }
}
