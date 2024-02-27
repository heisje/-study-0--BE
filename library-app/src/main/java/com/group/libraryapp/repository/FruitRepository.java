package com.group.libraryapp.repository;

import com.group.libraryapp.dto.FruitResponse;

import java.time.LocalDate;
import java.util.List;

public interface FruitRepository {

    boolean isExistFruit(long id);

    void saveFruit(String name, LocalDate warehousingDate, long price);

    List<FruitResponse> getFruits();

    void sellFruit(long id);

    long calculateSalesAmount(String name);

    long calculateNotSalesAmount(String name);
}
