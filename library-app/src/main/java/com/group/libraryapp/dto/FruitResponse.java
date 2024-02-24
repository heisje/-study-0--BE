package com.group.libraryapp.dto;

public class FruitResponse {
    private long saleAmount;
    private long notSalesAmount;

    public FruitResponse(long saleAmount, long notSalesAmount) {
        this.saleAmount = saleAmount;
        this.notSalesAmount = notSalesAmount;
    }
}
