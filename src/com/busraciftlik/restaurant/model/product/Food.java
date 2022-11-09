package com.busraciftlik.restaurant.model.product;

import com.busraciftlik.restaurant.enums.PortionSize;

import java.math.BigDecimal;

public class Food extends Product {

    public Food(BigDecimal price, String name, long preparationTime, PortionSize portionSize) {
        super(price, name, preparationTime, portionSize);
    }

}
