package com.mooncowpines.KinoStats.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.mooncowpines.KinoStats.Service.HomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/home")
@RequiredArgsConstructor
public class HomeController {
    
    private final HomeService homeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getHomeData(@PathVariable Long id){
        return ResponseEntity.ok().body(homeService.getHomeData(id));
    }
}
