package com.busraciftlik.restaurant.model.order;

import com.busraciftlik.restaurant.model.restaurant.Table;

public class Check {
    private Table table;

    public Check(Table table) {
        this.table = table;
    }


    @Override
    public String toString() {
        return "Check{" +
                "  table=" + table +
                '}';
    }

    public Table getTable() {
        return table;
    }
}
