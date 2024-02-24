package com.group.libraryapp.service;

import com.group.libraryapp.dto.FruitRequest;
import com.group.libraryapp.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(@Qualifier("sql") FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }


    public void saveFruit(FruitRequest request){
        fruitRepository.saveFruit(request.getName(), request.warehousingDate(), request.getPrice());
    }

    public void sellFruit(long id){
        if (fruitRepository.isExistFruit(id)){
            throw new IllegalArgumentException();
        }
        fruitRepository.sellFruit(id);
    }

    public Map<String, Long> findFruit(String name){
        long salesAmount = fruitRepository.calculateSalesAmount(name);
        long notSalesAmount = fruitRepository.calculateNotSalesAmount(name);
        Map<String, Long> response = new HashMap<>();
        response.put("salesAmount", salesAmount);
        response.put("notSalesAmount", notSalesAmount);

        return response;


    }
}
