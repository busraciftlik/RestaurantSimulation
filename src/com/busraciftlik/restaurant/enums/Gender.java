package com.busraciftlik.restaurant.enums;

public enum Gender {
    FEMALE('F'),
    MALE('M');

    private char genderCode;

    Gender(char genderCode) {
        this.genderCode = genderCode;
    }

}
