package com.example.simplereservationsapp.modules;

import com.google.firebase.database.Exclude;

public class Reservation {

    String ime;
    String datumOd;
    String datumDo;
    String opis;
    int boja;

    @Exclude
    String reservationID;


    public Reservation(){}

    public String getIme(){return ime;}
    public void setIme(String ime){this.ime=ime;}

    public String getDatumOd(){return datumOd;}
    public void setDatumOd(String datumOd){this.datumOd=datumOd;}

    public String getDatumDo(){return datumDo;}
    public void setDatumDo(String datumDo){this.datumDo=datumDo;}

    public String getOpis(){return opis;}
    public void setOpis(String opis){this.opis=opis;}

    public int getBoja(){return boja;}
    public void setBoja(int boja){this.boja=boja;}

    @Exclude
    public String getReservationID(){return reservationID;}
    @Exclude
    public void setReservationID(String reservationID){this.reservationID=reservationID;}


    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (!(o instanceof Reservation)) {
            return false;
        }

        Reservation r = (Reservation) o;

        // Compare the data members and return accordingly
        return this.reservationID.equals(r.getReservationID());
    }
}
