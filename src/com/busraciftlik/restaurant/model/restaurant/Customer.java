package com.busraciftlik.restaurant.model.restaurant;

import com.busraciftlik.restaurant.enums.PortionSize;
import com.busraciftlik.restaurant.model.order.BeverageOrder;
import com.busraciftlik.restaurant.model.order.FoodOrder;
import com.busraciftlik.restaurant.model.order.Order;
import com.busraciftlik.restaurant.model.product.Beverage;
import com.busraciftlik.restaurant.model.product.Food;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Customer {
    private String firstName;
    private String lastName;
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     *
     * @return orders listesi
     */
    public ArrayList<Order> giveOrder() {
        Food pizza = new Food(BigDecimal.valueOf(25.0), "Pizza Margherita", 2000, PortionSize.MEDIUM);
        Food salad = new Food(BigDecimal.valueOf(5.0), "Mediterranean Salad", 3000, PortionSize.LARGE);

        FoodOrder foodOrder = new FoodOrder();
        foodOrder.addFood(pizza);
        foodOrder.addFood(salad);

        Beverage drink = new Beverage(BigDecimal.valueOf(25.0), "Mocha", 1000, PortionSize.LARGE);
        BeverageOrder beverageOrder = new BeverageOrder();
        beverageOrder.addBeverage(drink);

        ArrayList<Order> orders = new ArrayList<>();
        orders.add(beverageOrder);
        orders.add(foodOrder);

        return orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
