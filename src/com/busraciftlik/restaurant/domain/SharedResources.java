package com.busraciftlik.restaurant.domain;

import com.busraciftlik.restaurant.model.order.Check;
import com.busraciftlik.restaurant.model.restaurant.Table;

import java.util.concurrent.LinkedBlockingQueue;

public final class SharedResources {

    public static final LinkedBlockingQueue<Check> PENDING_ORDERS_QUEUE = new LinkedBlockingQueue<>();
    public static final LinkedBlockingQueue<Check> PREPARED_ORDERS_QUEUE = new LinkedBlockingQueue<>();
    public static final LinkedBlockingQueue<Table> SITTING_CUSTOMERS_QUEUE = new LinkedBlockingQueue<>();




}
