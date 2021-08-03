package com.example.simplereservationsapp.enums;

public enum ReservationMode {
    CREATE_MODE(0), PREVIEW_MODE(1);

    private final int value;
    private ReservationMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
