package com.busraciftlik.restaurant.model.employee;

import com.busraciftlik.restaurant.domain.SharedResources;
import com.busraciftlik.restaurant.enums.Gender;
import com.busraciftlik.restaurant.model.order.Check;
import com.busraciftlik.restaurant.model.restaurant.Table;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Waiter extends Employee implements Runnable {

    public Waiter(String fullName, Gender gender) {
        super(fullName, gender);
    }

    /**
     * parametre olarak geçilen preparedCheck table a atanır ve teslim edilmesi için bir süre bekletilir.
     * @param preparedCheck
     */
    public void deliverOrders(Check preparedCheck) {
        Table table = preparedCheck.getTable();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        table.customersLeft();
    }

    @Override
    public void run() {
        while(true) {
            while(!SharedResources.SITTING_CUSTOMERS_QUEUE.isEmpty()){
                Table table = SharedResources.SITTING_CUSTOMERS_QUEUE.poll();
                if(Objects.isNull(table)) break;
                System.out.println(Thread.currentThread().getName() + " is taking the order for the table number "+table.getTableNo());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Check newCheck = new Check(table);
                SharedResources.PENDING_ORDERS_QUEUE.add(newCheck);
            }
            while(!SharedResources.PREPARED_ORDERS_QUEUE.isEmpty()){
                Check check = SharedResources.PREPARED_ORDERS_QUEUE.poll();
                if(Objects.isNull(check)) break;
                System.out.println(Thread.currentThread().getName() + " is delivering the order of table number " + check.getTable().getTableNo());
                this.deliverOrders(check);
            }
        }
    }
}

