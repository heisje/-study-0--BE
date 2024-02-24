package com.group.libraryapp.service;

import com.group.libraryapp.dto.FruitRequest;
import com.group.libraryapp.repository.FruitRepositroy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FruitService {

    private final FruitRepositroy fruitRepositroy;

    public FruitService(JdbcTemplate jdbcTemplate) {
        fruitRepositroy = new FruitRepositroy(jdbcTemplate);
    }

    public void saveFruit(FruitRequest request){
        fruitRepositroy.saveFruit(request.getName(), request.warehousingDate(), request.getPrice());
    }

    public void sellFruit(long id){
        if (fruitRepositroy.isExistFruit(id)){
            throw new IllegalArgumentException();
        }
        fruitRepositroy.sellFruit(id);
    }

    public Map<String, Long> findFruit(String name){
        long salesAmount = fruitRepositroy.calculateSalesAmount(name);
        long notSalesAmount = fruitRepositroy.calculateNotSalesAmount(name);
        Map<String, Long> response = new HashMap<>();
        response.put("salesAmount", salesAmount);
        response.put("notSalesAmount", notSalesAmount);
        return response;
    }
}
