package com.busraciftlik.restaurant.model.employee;

import com.busraciftlik.restaurant.enums.Gender;

public abstract class Employee {
    private String fullName;
    private Gender gender;

    public Employee(String fullName, Gender gender) {
        this.fullName = fullName;
        this.gender = gender;
    }


    public String getFullName() {
        return fullName;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "fullName='" + fullName + '\'' +
                ", gender=" + gender +
                '}';
    }
}
