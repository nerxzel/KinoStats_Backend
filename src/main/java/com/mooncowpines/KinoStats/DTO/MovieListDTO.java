package com.mooncowpines.KinoStats.DTO;

import java.util.List;

public record MovieListDTO(
    Long movieListId,
    String name,
    Integer movieCount,
    List<MovieCardDTO> movies,
    Boolean isWatchlist
) {
    
}
