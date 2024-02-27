package com.group.libraryapp.dto;

import com.group.libraryapp.domain.Fruit;

import java.time.LocalDate;

public class FruitResponse {
    private final String name;
    private final LocalDate warehousingDate;
    private final long price;

    public FruitResponse(Fruit fruit) {
        this.name = fruit.getName();
        this.warehousingDate = fruit.getWarehousingDate();
        this.price = fruit.getPrice();
    }

    public String getName() {
        return name;
    }

    public LocalDate getWarehousingDate() {
        return warehousingDate;
    }

    public long getPrice() {
        return price;
    }
}
