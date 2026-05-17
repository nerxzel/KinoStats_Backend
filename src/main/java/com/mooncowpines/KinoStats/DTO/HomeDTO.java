package com.mooncowpines.KinoStats.DTO;

import java.util.List;

public record HomeDTO(
    List<MovieCardDTO> watchList,
    MovieCardDTO lastSeen,
    List<MovieCardDTO> justWatched
) {
    
}
