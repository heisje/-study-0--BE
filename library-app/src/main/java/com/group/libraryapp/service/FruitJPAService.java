package com.group.libraryapp.service;

import com.group.libraryapp.domain.Fruit;
import com.group.libraryapp.domain.FruitJPARepository;
import com.group.libraryapp.dto.FruitRequest;
import com.group.libraryapp.dto.FruitResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Primary
public class FruitJPAService implements FruitService {
    private final FruitJPARepository fruitJPARepository ;

    public FruitJPAService(FruitJPARepository fruitJPARepository) {
        this.fruitJPARepository = fruitJPARepository;
    }

    public void saveFruit(FruitRequest request){
        fruitJPARepository.save(new Fruit(request.getName(), request.getWarehousingDate(), request.getPrice()));
    }

    public List<FruitResponse> getFruits(){
        return fruitJPARepository.findAll().stream().map(FruitResponse::new).collect(Collectors.toList());
    }

    public void sellFruit(long id){
        Fruit fruit = fruitJPARepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        fruit.updateIsSale(false);
        fruitJPARepository.save(fruit);
    }

    public Map<String, Long> findFruit(String name){
        long salesAmount = fruitJPARepository.sumPriceByNameAndIsSale(name, true).orElse(0L);
        long notSalesAmount = fruitJPARepository.sumPriceByNameAndIsSale(name, false).orElse(0L);
        Map<String, Long> response = new HashMap<>();
        response.put("salesAmount", salesAmount);
        response.put("notSalesAmount", notSalesAmount);

        return response;
    }
}
