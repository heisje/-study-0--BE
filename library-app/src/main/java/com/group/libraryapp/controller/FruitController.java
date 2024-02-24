package com.group.libraryapp.controller;

import com.group.libraryapp.dto.FruitRequest;
import com.group.libraryapp.service.FruitService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class FruitController {

    JdbcTemplate jdbcTemplate;
    FruitService fruitService;

    public FruitController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.fruitService = new FruitService(jdbcTemplate);
    }

    @PostMapping("/fruit")
    public void saveFruit(@RequestBody FruitRequest request){
        fruitService.saveFruit(request);
    }

    @PutMapping("/fruit")
    public void sellFruit(@RequestParam long id){
        fruitService.sellFruit(id);
    }

    @GetMapping("/fruit")
    public Map<String, Long> getFruit(@RequestParam String name){
        return fruitService.findFruit(name);
    }

}
