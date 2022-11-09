package com.busraciftlik.restaurant.model.product;

import com.busraciftlik.restaurant.enums.PortionSize;

import java.math.BigDecimal;

public abstract class Product {

    private BigDecimal price;
    private String name;
    private long preparationTime;
    private PortionSize portionSize;
    private boolean isReady;
    public Product(BigDecimal price, String name, long preparationTime, PortionSize portionSize) {
        this.price = price;
        this.name = name;
        this.preparationTime = preparationTime;
        this.portionSize = portionSize;
        this.isReady = false;
    }

    public String getName() {
        return name;
    }

    public long getPreparationTime() {
        return preparationTime;
    }

    public void setReady(boolean isReady){
        this.isReady = isReady;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", preparationTime=" + preparationTime +
                ", portionSize=" + portionSize +
                '}';
    }
}
