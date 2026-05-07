package com.mooncowpines.KinoStats.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mooncowpines.KinoStats.DTO.MovieDetailsDTO;
import com.mooncowpines.KinoStats.DTO.TmdbMovieResponse;
import com.mooncowpines.KinoStats.DTO.TmdbMovieSearchResponse;
import com.mooncowpines.KinoStats.DTO.TmdbMovieSearchResponseWrapper;

@Service
public class TmdbService {
    private final WebClient webClient;

    public TmdbService(@Value("${tmdb.api.key}") String apiKey,
                       @Value("${tmdb.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Authorization", "Bearer " + apiKey)
            .build();
    }

    public TmdbMovieResponse fetchMovie(Long tmdbId) {
        return webClient.get()
            .uri("/movie/{id}", tmdbId)
            .retrieve()
            .bodyToMono(TmdbMovieResponse.class)
            .block();
    }

    public List<TmdbMovieSearchResponse> searchMovies(String searchText){
        TmdbMovieSearchResponseWrapper wrapper = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/search/movie")
                .queryParam("query", searchText)
                .build())
            .retrieve()
            .bodyToMono(TmdbMovieSearchResponseWrapper.class)
            .block();

        return wrapper != null ? wrapper.results() : List.of();
    }

    public MovieDetailsDTO getMovieDetails(Long tmdbId){
        TmdbMovieResponse response = fetchMovie(tmdbId);
        return mapToDto(response);
    }

    public MovieDetailsDTO mapToDto(TmdbMovieResponse response) {
        return new MovieDetailsDTO(
            response.id(),
            response.title(),
            response.runtime(),
            response.releaseDate(),
            response.productionCountries().stream().map(c -> c.name()).collect(Collectors.joining(", ")),
            response.genres().stream().map(g -> g.name()).collect(Collectors.joining(", ")),
            response.backdropPath(),
            response.posterPath(),
            response.overview()
        );
    }
}
