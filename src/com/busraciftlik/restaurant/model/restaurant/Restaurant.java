package com.busraciftlik.restaurant.model.restaurant;

import com.busraciftlik.restaurant.domain.SharedResources;
import com.busraciftlik.restaurant.enums.Gender;
import com.busraciftlik.restaurant.ex.AvailableTableNotFoundException;
import com.busraciftlik.restaurant.model.employee.Chef;
import com.busraciftlik.restaurant.model.employee.Waiter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class Restaurant {
    private ConcurrentLinkedQueue<Chef> chefs;
    private ArrayList<Waiter> waiters;
    private ArrayList<Table> tables;
    private LinkedList<Customer> customers = new LinkedList<>();

    public Restaurant() {
        this.chefs = initChefs();
        this.waiters = initWaiters();
        this.tables = initTables();
    }

    /**
     * oluşturulan waiters listesi için waiterlar oluşturur ve listeye ekler
     *
     * @return waiters listesi
     */
    private ArrayList<Waiter> initWaiters() {
        ArrayList<Waiter> waiters = new ArrayList<>();
        waiters.add(new Waiter("John", Gender.MALE));
        waiters.add(new Waiter("Julia", Gender.FEMALE));
        waiters.add(new Waiter("Griffin", Gender.MALE));
        return waiters;
    }

    /**
     * oluşturulan chefs listesi için yeni chefler oluşturur ve listeye ekler
     *
     * @return chefs listesi
     */

    private ConcurrentLinkedQueue<Chef> initChefs() {
        ConcurrentLinkedQueue<Chef> chefs = new ConcurrentLinkedQueue<Chef>();
        chefs.add(new Chef("Danilo", Gender.MALE));
        chefs.add(new Chef("Mehmet", Gender.MALE));
        return chefs;
    }

    /**
     * oluşturulan tables listesi için yeni tablelar oluşturur ve listeye ekler
     *
     * @return tables listesi
     */
    private ArrayList<Table> initTables() {
        ArrayList<Table> tables = new ArrayList<>();
        tables.add(new Table(4, 1));
        tables.add(new Table(4, 2));
        tables.add(new Table(2, 3));
        tables.add(new Table(2, 4));
        tables.add(new Table(1, 5));
        return tables;
    }

    /**
     * parametre olarak geçilen customerGroup listesinin uzunluğu 4 ten büyük değilse
     * customerGroup kabul edilir. Uygun masa bulunarak müşteriler bu masaya oturtulur.
     *
     * @param customerGroup
     * @return table
     */
    public Table acceptCustomers(ArrayList<Customer> customerGroup) {
        if (customerGroup.size() > 4) {
            System.out.println(Thread.currentThread().getName() + " ->Groups of up to 4 customers are accepted.");
            return null;
        } else {
            Table table = getAvailableTable(customerGroup.size());
            table.customersSit(customerGroup);
            return table;
        }
    }

    /**
     * Table listesini gezerek müşteri sayısına uygun boş masa olup olmadığını kontrol eder.
     *
     * @param customerGroupSize
     * @return eğer uygun masa varsa true, aksi halde false döner
     */

    public boolean availableTableIsExists(int customerGroupSize) {
        for (Table table : tables) {
            if (table.isEmpty() && table.getSeaterCount() >= customerGroupSize) {
                return true;
            }
        }
        return false;
    }

    /**
     * Table listesini gezerek müşteri sayısına uygun boş masayı getirir.
     *
     * @param customerGroupSize
     * @return table  müşteri grubuna  uygun masa
     */
    public Table getAvailableTable(int customerGroupSize) {
        for (Table table : tables) {
            if (table.isEmpty() && table.getSeaterCount() >= customerGroupSize) {
                return table;
            }
        }
        throw new AvailableTableNotFoundException();
    }

    /**
     * restoranın çalışmaya başlaması için çağrılır.
     *
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        runWaiters();
        System.out.println();
        runChefs();
        startWorkDay();
    }

    /**
     * chefs listesindeki her bir chef için thread oluşturur
     * oluşturulan therad için isim set eder ve thread i başlatır.
     */
    private void runChefs() {
        for (Chef chef : chefs) {
            Thread thread = new Thread(chef);
            thread.setName(chef.getFullName());
            thread.start();
            System.out.println("A thread was created for " + thread.getName() + " Chef");
        }
    }

    /**
     * waiters listesindeki her bir waiter için thread oluşturur
     * oluşturulan therad için isim set eder ve thread i başlatır.
     */
    private void runWaiters() {
        for (Waiter waiter : waiters) {
            Thread thread = new Thread(waiter);
            thread.setName(waiter.getFullName());
            thread.start();
            System.out.println("A thread was created for " + thread.getName());
        }
    }

    /**
     * Restoran iş günü başlatılır. Her 3 saniyede bir,
     * rastgele oluşturulan müşteri grubunu boş masa olması durumnda kabul eder.
     *
     * @throws InterruptedException
     */
    private void startWorkDay() throws InterruptedException {
        System.out.println("\nRestaurant starts working.\n");
        Thread thread = Thread.currentThread();
        thread.setName("Restoran");
        for (; ; ) {
            ArrayList<Customer> customerGroup = generateRandomCustomerGroup();
            System.out.println(Thread.currentThread().getName() + " -> Customer group arrived " + customerGroup.size() + " customers");
            if (this.availableTableIsExists(customerGroup.size())) {
                Table table = this.acceptCustomers(customerGroup);
                SharedResources.SITTING_CUSTOMERS_QUEUE.add(table);
            } else {
                System.out.println(Thread.currentThread().getName() + " ->No empty tables for a group of " + customerGroup.size() + " customers");
            }
            TimeUnit.SECONDS.sleep(3);
        }
    }

    /**
     * 1 ve 6  aralığında rastgele customer üretir ve customerGroup listesine ekler
     *
     * @return customersGroup
     */
    public ArrayList<Customer> generateRandomCustomerGroup() {
        Random random = new Random();
        ArrayList<Customer> customerGroup = new ArrayList<>();
        for (int i = 0; i < random.nextInt(1, 6); i++) {
            customerGroup.add(new Customer("Sample", "Customer"));
        }
        return customerGroup;
    }

}
