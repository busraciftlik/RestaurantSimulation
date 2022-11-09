package com.busraciftlik.restaurant.ex;

public class AvailableTableNotFoundException extends RuntimeException{

   public AvailableTableNotFoundException(){
        super("Available table not found");
    }
}
