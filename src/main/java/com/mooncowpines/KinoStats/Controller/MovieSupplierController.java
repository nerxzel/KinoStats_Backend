package com.mooncowpines.KinoStats.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooncowpines.KinoStats.DTO.MovieDetailsDTO;
import com.mooncowpines.KinoStats.DTO.TmdbMovieSearchResponse;
import com.mooncowpines.KinoStats.Service.TmdbService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/movies")
@RequiredArgsConstructor
public class MovieSupplierController {
    private final TmdbService tmdbService;

    @GetMapping("/{id}")
    ResponseEntity<?> getMovieDetails(@PathVariable Long id){
        MovieDetailsDTO response = tmdbService.getMovieDetails(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/{query}")
    ResponseEntity<?> getSearchResults(@PathVariable String query){
        List<TmdbMovieSearchResponse> responses = tmdbService.searchMovies(query);
        return ResponseEntity.ok().body(responses);
    }

    /*@GetMapping("TEST")
    ResponseEntity<?> getCredits(){
        TmdbCreditsResponse response = tmdbService.fetchMovieCredits(129L);
        return ResponseEntity.ok().body(response);
    }*/
}
