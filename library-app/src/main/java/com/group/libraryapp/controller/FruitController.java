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
@RequestMapping("/api/v2/fruit")
public class FruitController {

    JdbcTemplate jdbcTemplate;
    FruitService fruitService;

    public FruitController(JdbcTemplate jdbcTemplate, FruitService fruitService) {
        this.jdbcTemplate = jdbcTemplate;
        this.fruitService = fruitService;
    }

    @PostMapping("")
    public void saveFruit(@RequestBody FruitRequest request){
        fruitService.saveFruit(request);
    }

    @PutMapping("")
    public void sellFruit(@RequestParam long id){
        fruitService.sellFruit(id);
    }

    @GetMapping("")
    public List<FruitResponse> getFruit(){
        return fruitService.getFruits();
    }

    @GetMapping("/sum")
    public Map<String, Long> findFruit(@RequestParam String name){
        return fruitService.findFruit(name);
    }

    @GetMapping("/count")
    public Map<String, Long> countFruit(@RequestParam String name){
        return fruitService.countFruit(name);
    }

    @GetMapping("/list")
    public List<FruitResponse> isSaleByPriceFruit(@RequestParam String option, long price){
        return fruitService.isSaleByPriceFruit(option, price);
    }
}
