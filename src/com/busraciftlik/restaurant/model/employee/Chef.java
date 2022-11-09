package com.busraciftlik.restaurant.model.employee;

import com.busraciftlik.restaurant.domain.SharedResources;
import com.busraciftlik.restaurant.enums.Gender;
import com.busraciftlik.restaurant.model.order.BeverageOrder;
import com.busraciftlik.restaurant.model.order.Check;
import com.busraciftlik.restaurant.model.order.FoodOrder;
import com.busraciftlik.restaurant.model.order.Order;
import com.busraciftlik.restaurant.model.product.Beverage;
import com.busraciftlik.restaurant.model.product.Food;
import com.busraciftlik.restaurant.model.product.Product;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Chef extends Employee implements Runnable {

    private int currentPreparingTableNo;

    public Chef(String fullName, Gender gender) {
        super(fullName, gender);
    }

    /**
     * gelen yemek siparişindeki yemekleri pişirir
     *
     * @param foodOrder gelen yemek siparişi
     * @return pişirilen yemekler
     */
    public List<Food> cook(FoodOrder foodOrder) {
            List<Food> foods = foodOrder.getFoods();
            for (Food food : foods) {
                this.prepare(food);
            }
            return foods;
    }

    /**
     * parametre olarak gelen içeçek siparişindeki içeceği içecek listesindeki her bir içecek için
     * gezerek içeceği getirir ve hazırlar
     * @param beverageOrder
     * @return hazırlanan içecekler
     */
    public List<Beverage> prepare(BeverageOrder beverageOrder) {
        List<Beverage> beverages = beverageOrder.getBeverages();
        for (Beverage beverage : beverages) {
            this.prepare(beverage);
        }
        return beverages;
    }
    /**
     * parametre olarak geçilen ürünü hazırlama süresince bekleterek hazır hale getirir
     *
     * @param product
     */
    private void prepare(Product product) {
        try {
            System.out.println(Thread.currentThread().getName() +" -> " + product.getName() + " preparing for table "+ currentPreparingTableNo + " " + product.getPreparationTime() + " milliseconds by Chef " + getFullName() + "  (Time -> " + LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))+")");
            //System.out.println(Thread.currentThread().getName() +" -> " + currentPreparingTableNo + " numaralı masa için " + product.getName() + " hazırlıyor. " + product.getPreparationTime() + " milisaniye sürecek "+ "  (Time -> " + LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))+")");
            TimeUnit.MILLISECONDS.sleep(product.getPreparationTime());
            product.setReady(true);

        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" ->An error occurred during preparing " + product.getName());
            product.setReady(false);
        }
    }

    @Override
    public void run() {
        while (true) {
            while (!SharedResources.PENDING_ORDERS_QUEUE.isEmpty()) {
                Check check = SharedResources.PENDING_ORDERS_QUEUE.poll();
                if(Objects.isNull(check)) break;
                this.currentPreparingTableNo = check.getTable().getTableNo();
                System.out.println(Thread.currentThread().getName() + " is preparing the order for the table number " + check.getTable().getTableNo());
                List<Order> orders = check.getTable().getTablesOrders();
                for (Order order : orders) {
                    if (order instanceof FoodOrder) {
                        cook((FoodOrder) order);
                    } else if (order instanceof BeverageOrder) {
                        prepare((BeverageOrder) order);
                    }
                }
                SharedResources.PREPARED_ORDERS_QUEUE.add(check);

            }
        }
    }
}
