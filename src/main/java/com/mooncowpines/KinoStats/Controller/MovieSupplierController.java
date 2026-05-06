package com.mooncowpines.KinoStats.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooncowpines.KinoStats.DTO.TmdbMovieResponse;
import com.mooncowpines.KinoStats.Service.TmdbService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/movies")
@RequiredArgsConstructor
public class MovieSupplierController {
    private final TmdbService tmdbService;

    @GetMapping("/{id}")
    ResponseEntity<?> getMovieDetails(@PathVariable Long id){
        TmdbMovieResponse response = tmdbService.fetchMovie(id);
        return ResponseEntity.ok().body(response);
    }

}
