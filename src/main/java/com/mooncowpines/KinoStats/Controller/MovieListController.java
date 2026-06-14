package com.mooncowpines.KinoStats.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooncowpines.KinoStats.DTO.MovieListAddDTO;
import com.mooncowpines.KinoStats.DTO.MovieListDTO;
import com.mooncowpines.KinoStats.DTO.MovieListRequestDTO;
import com.mooncowpines.KinoStats.Service.MovieListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/lists")
@RequiredArgsConstructor
public class MovieListController {

    private final MovieListService movieListService;

    @PostMapping
    public ResponseEntity<MovieListDTO> createList(@RequestBody MovieListRequestDTO request) {
        MovieListDTO list = movieListService.createList(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MovieListDTO>> getListsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(movieListService.getListsByUser(userId));
    }

    @GetMapping("/{listId}")
    public ResponseEntity<MovieListDTO> getList(@PathVariable Long listId) {
        return ResponseEntity.ok(movieListService.getList(listId));
    }

    @PutMapping("/{listId}")
    public ResponseEntity<MovieListDTO> updateList(@PathVariable Long listId, @RequestBody MovieListRequestDTO request) {
        return ResponseEntity.ok(movieListService.updateList(listId, request));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFilmToList(@RequestBody MovieListAddDTO request) {
        movieListService.addFilmToList(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{listId}/film/{filmId}")
    public ResponseEntity<Void> removeFilmFromList(@PathVariable Long listId,
                                                    @PathVariable Long filmId) {
        movieListService.removeFilmFromList(listId, filmId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(@PathVariable Long listId) {
        movieListService.deleteList(listId);
        return ResponseEntity.noContent().build();
    }
}
