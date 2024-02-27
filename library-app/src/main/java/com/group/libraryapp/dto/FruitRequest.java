package com.group.libraryapp.dto;

import java.time.LocalDate;

public class FruitRequest {
    private final String name;
    private final LocalDate warehousingDate;
    private final long price;

    public FruitRequest(String name, LocalDate warehousingDate, long price) {
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
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
