package com.busraciftlik.restaurant.model.restaurant;

import com.busraciftlik.restaurant.model.order.Order;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Table {
    private int seaterCount;
    private ArrayList<Customer> sittingCustomers;
    private ArrayList<Order> tablesOrders;
    private boolean isEmpty;
    private final int tableNo;

    public Table(int seaterCount, int tableNo) {
        this.tableNo = tableNo;
        tablesOrders = new ArrayList<>();
        this.seaterCount = seaterCount;
        isEmpty = true;
    }

    public int getTableNo() {
        return tableNo;
    }

    public int getSeaterCount() {
        return seaterCount;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void customersLeft(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(15);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() +" ->Customer group left " + seaterCount + " seater table");
                isEmpty = true;
                sittingCustomers = null;
            }
        }).start();

    }

    /**
     * parametre olarak geçilen customerGroup listenin eleman sayısı koltuk sayısıdan
     * büyük değilse customerGroup sittigCustomers a atanır. isEmpty alanı false olarak atanır.
     * @param customerGroup
     */
    public void customersSit(ArrayList<Customer> customerGroup){
        if(customerGroup.size() > seaterCount) {
            System.out.println(Thread.currentThread().getName() +" -> Exceeded the number of people who can sit at the table");
        } else {
            this.sittingCustomers = customerGroup;
            System.out.println(Thread.currentThread().getName() +" -> Customers sat at a table ");
            isEmpty = false;
        }
        createOrders();

    }

    /**
     * sittingCustomers listesindeki her bir customer sipariş verir
     * bu siparişler tablesOrders listesine eklenir.
     */
    private void createOrders(){
        for(Customer customer : sittingCustomers) {
            ArrayList<Order> orders = customer.giveOrder();
            tablesOrders.addAll(orders);
        }
    }

    public ArrayList<Order> getTablesOrders() {
        return tablesOrders;
    }
}