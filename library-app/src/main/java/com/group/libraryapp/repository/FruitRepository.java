package com.group.libraryapp.repository;

import java.time.LocalDate;

public interface FruitRepository {

    boolean isExistFruit(long id);

    void saveFruit(String name, LocalDate warehousingDate, long price);

    void sellFruit(long id);

    long calculateSalesAmount(String name);

    long calculateNotSalesAmount(String name);
}
