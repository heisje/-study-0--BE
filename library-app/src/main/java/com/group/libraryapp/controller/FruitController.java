package com.group.libraryapp.controller;

import com.group.libraryapp.dto.FruitRequest;
import com.group.libraryapp.dto.FruitResponse;
import com.group.libraryapp.service.FruitJdbcService;
import com.group.libraryapp.service.FruitService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class FruitController {

    JdbcTemplate jdbcTemplate;
    FruitService fruitService;

    public FruitController(JdbcTemplate jdbcTemplate, FruitService fruitService) {
        this.jdbcTemplate = jdbcTemplate;
        this.fruitService = fruitService;
    }

    @PostMapping("/fruit")
    public void saveFruit(@RequestBody FruitRequest request){
        fruitService.saveFruit(request);
    }

    @PutMapping("/fruit")
    public void sellFruit(@RequestParam long id){
        fruitService.sellFruit(id);
    }

    @GetMapping("/fruit/sum")
    public Map<String, Long> findFruit(@RequestParam String name){
        return fruitService.findFruit(name);
    }

    @GetMapping("/fruit")
    public List<FruitResponse> getFruit(){
        return fruitService.getFruits();
    }

}
