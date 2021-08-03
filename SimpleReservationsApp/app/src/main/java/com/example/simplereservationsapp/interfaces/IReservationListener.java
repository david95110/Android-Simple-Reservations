package com.example.simplereservationsapp.interfaces;

import com.example.simplereservationsapp.modules.Reservation;

import java.util.ArrayList;

public interface IReservationListener {
    void onReservationSaved(boolean saved);
    void onReservationsReceived(ArrayList<Reservation> reservations);
    void onReservationDeleted();
    void onReservationUpdated();
}
