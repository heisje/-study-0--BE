package com.group.libraryapp.dto;

public class FruitSellResponse {
    private long saleAmount;
    private long notSalesAmount;

    public FruitSellResponse(long saleAmount, long notSalesAmount) {
        this.saleAmount = saleAmount;
        this.notSalesAmount = notSalesAmount;
    }
}
