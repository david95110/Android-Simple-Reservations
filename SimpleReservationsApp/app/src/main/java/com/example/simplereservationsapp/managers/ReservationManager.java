package com.example.simplereservationsapp.managers;

import androidx.annotation.NonNull;

import com.example.simplereservationsapp.interfaces.IReservationListener;
import com.example.simplereservationsapp.modules.Reservation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReservationManager {
    private static final String RESERVATIONS="reservations";
    private final DatabaseReference databaseReservationsReference;

    private ReservationManager(){
        databaseReservationsReference=AppManager.getInstance().getFirebaseDatabase().getReference(RESERVATIONS);
    }

    public DatabaseReference getReservationsReference() {
        return databaseReservationsReference;
    }

    private static class SingletonHolder{
        public static final ReservationManager instance=new ReservationManager();
    }

    public static ReservationManager getInstance(){return SingletonHolder.instance;}


    public void saveReservation(IReservationListener listener, Reservation res) {
        String reservationID=databaseReservationsReference.push().getKey();
        if(reservationID!=null)
        {
            databaseReservationsReference.child(reservationID).setValue(res);
            listener.onReservationSaved(true);

        }else
            listener.onReservationSaved(false);
    }


    public void getReservations(IReservationListener listener){
        databaseReservationsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              databaseReservationsReference.removeEventListener(this);
              if(snapshot.exists()){
                  ArrayList<Reservation> reservations=new ArrayList<>();
                  for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                      Reservation res=dataSnapshot.getValue(Reservation.class);
                      res.setReservationID(dataSnapshot.getKey());
                      reservations.add(res);
                  }
                  listener.onReservationsReceived(reservations);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
