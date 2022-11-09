package com.busraciftlik.restaurant.model.order;

import com.busraciftlik.restaurant.model.product.Food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodOrder extends Order {

    private ArrayList<Food> foods = new ArrayList<>();
    private static final int FOOD_ORDER_LIMIT = 2;

    public FoodOrder() {
    }

    /**
     * foods listesinin uzunluğu FOOD_ORDER_LIMIT e eşit ise parametre olarak geçilen food listeye eklenmez
     * eşit değilse listeye eklenir ve true döner
     * @param food
     * @return
     */
    public boolean addFood(Food food) {
        if (foods.size() == FOOD_ORDER_LIMIT) {
            return false;
        } else {
            foods.add(food);
            return true;
        }
    }

    public List<Food> getFoods() {
        return Collections.unmodifiableList(foods);
    }
}


