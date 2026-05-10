package com.mooncowpines.KinoStats.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooncowpines.KinoStats.DTO.StatsRequestDTO;
import com.mooncowpines.KinoStats.DTO.StatsResponseDTO;
import com.mooncowpines.KinoStats.Service.StatsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/stats")
@RequiredArgsConstructor
public class StatsController {
    
    private final StatsService statsService;

    @GetMapping("/get")
    public ResponseEntity<?> getStats(@RequestBody StatsRequestDTO request){
        StatsResponseDTO stats = statsService.calculateStats(request);
        return ResponseEntity.ok().body(stats);
    }
}
