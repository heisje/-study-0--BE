package com.group.libraryapp.service;

import com.group.libraryapp.domain.Fruit;
import com.group.libraryapp.domain.FruitJPARepository;
import com.group.libraryapp.dto.FruitRequest;
import com.group.libraryapp.dto.FruitResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FruitService {


    void saveFruit(FruitRequest request);

    List<FruitResponse> getFruits();

    void sellFruit(long id);

    Map<String, Long> findFruit(String name);
}
