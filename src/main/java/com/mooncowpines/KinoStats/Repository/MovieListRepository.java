package com.mooncowpines.KinoStats.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mooncowpines.KinoStats.Model.MovieList;

@Repository
public interface MovieListRepository extends JpaRepository<MovieList, Long>{
    List<MovieList> findByUserId(Long userId);
}
