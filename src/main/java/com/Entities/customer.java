package com.Entities;

public class customer {
 private int id;
 private String name;
 private String trade;
 private String productsInterest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getProductsInterest() {
        return productsInterest;
    }

    public void setProductsInterest(String productsInterest) {
        this.productsInterest = productsInterest;
    }
}
