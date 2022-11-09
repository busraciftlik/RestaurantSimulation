package com.busraciftlik.restaurant.model.order;

import com.busraciftlik.restaurant.model.product.Beverage;
import com.busraciftlik.restaurant.model.product.Food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeverageOrder extends Order {
    private ArrayList<Beverage> beverages = new ArrayList<>();
    private static final int BEVERAGE_ORDER_LIMIT = 1;

    public BeverageOrder(){

    }

    /**
     * beverages listesinin uzunluğu BEVERAGE_ORDER_LIMIT e eşit ise parametre olrak geçilen beverage
     * listeye eklenmez
     * eşit değilse listeye beverage eklenir
     * @param beverage
     * @return
     */
    public boolean addBeverage(Beverage beverage) {
        if(beverages.size()==BEVERAGE_ORDER_LIMIT){
            return false;
        }else{
            beverages.add(beverage);
            return true;
        }
    }

    public List<Beverage> getBeverages() {
        return Collections.unmodifiableList(beverages);
    }

}

