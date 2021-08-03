package com.example.simplereservationsapp.managers;

import com.example.simplereservationsapp.activities.AddReservationActivity;
import com.example.simplereservationsapp.interfaces.IReservationListener;
import com.example.simplereservationsapp.modules.Reservation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppManager {

    private final FirebaseDatabase firebaseDatabase;
    private Reservation reservationToDisplay;


    private AppManager(){
        firebaseDatabase=FirebaseDatabase.getInstance();


    }

    public void saveReservation(IReservationListener listener, Reservation res) {
        ReservationManager.getInstance().saveReservation(listener,res);
    }
    public void getReservations(IReservationListener listener){
        ReservationManager.getInstance().getReservations(listener);
    }

    public void setReservationToDisplay(Reservation reservation) {
        reservationToDisplay=reservation;
    }
    public Reservation getReservationToDisplay(){return reservationToDisplay;}

    public void deleteReservation(AddReservationActivity listener, Reservation resToDelete) {
        ReservationManager.getInstance().deleteReservation(listener,resToDelete);
    }

    public void updateReservation(AddReservationActivity listener, Reservation res) {
        ReservationManager.getInstance().updateReservation(listener,res);
    }

    private static class SingletonHolder{
        public static final AppManager instance=new AppManager();
    }


    public static AppManager getInstance(){return SingletonHolder.instance;}

    public FirebaseDatabase getFirebaseDatabase(){return firebaseDatabase;}
    public DatabaseReference getReservationsReference(){return ReservationManager.getInstance().getReservationsReference();}




}
