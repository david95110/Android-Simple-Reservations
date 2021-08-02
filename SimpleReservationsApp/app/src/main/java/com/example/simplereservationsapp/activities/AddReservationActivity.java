package com.example.simplereservationsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.simplereservationsapp.R;
import com.example.simplereservationsapp.interfaces.IReservationListener;
import com.example.simplereservationsapp.managers.AppManager;
import com.example.simplereservationsapp.modules.Reservation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddReservationActivity extends AppCompatActivity implements View.OnClickListener, IReservationListener {

    private static final String TAG="AddReservationActivity";
    int mDefaultColor;
    Button mBtnSacuvaj;
    EditText mDatumOd,mDatumDo,mTextIme,mTextOpis;
    EditText clickedDate;
    TextView mTextViewOdaberiBoju;
    View mViewDisplayColor;
    DatePickerDialog.OnDateSetListener setDatumListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String month_name = new DateFormatSymbols().getMonths()[month];
                String date=dayOfMonth+" "+month_name+" "+year;
                clickedDate.setText(date);
        }
    };;

    Thread closeActivityThread = new Thread(){
        @Override
        public void run() {
            try {
                Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                AddReservationActivity.this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        initialiseComponents();

    }

    private void createDatePicker() {
        Calendar calendar= Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog=new DatePickerDialog(AddReservationActivity.this,setDatumListener,year,month,day);
        dialog.show();
    }

    private void initialiseComponents() {
        mBtnSacuvaj=(Button)findViewById(R.id.buttonSacuvaj);
        mDatumOd=(EditText) findViewById(R.id.editTextDatumOd);
        mDatumDo=(EditText) findViewById(R.id.editTextDatumDo);
        mTextIme=(EditText) findViewById(R.id.editTextIme);
        mTextOpis=(EditText) findViewById(R.id.editTextOpis);
        mTextViewOdaberiBoju=(TextView)findViewById(R.id.textViewOdaberiBoju);
        mViewDisplayColor=(View)findViewById(R.id.viewDisplayColor);

        mDefaultColor= ContextCompat.getColor(AddReservationActivity.this,R.color.red);

        mTextViewOdaberiBoju.setOnClickListener(this);
        mBtnSacuvaj.setOnClickListener(this);
        mDatumOd.setOnClickListener(this);
        mDatumDo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonSacuvaj){
            createReservation();

        }
        else if(v.getId()==R.id.editTextDatumOd){
            clickedDate=mDatumOd;
            createDatePicker();
        }
        else if(v.getId()==R.id.editTextDatumDo){
            clickedDate=mDatumDo;
            createDatePicker();
        }
        else if(v.getId()==R.id.textViewOdaberiBoju){
            openColorPicker();
        }

    }

    private void createReservation() {
        Reservation res=new Reservation();
        res.setIme(mTextIme.getText().toString());
        res.setOpis(mTextOpis.getText().toString());
        res.setDatumOd(mDatumOd.getText().toString());
        res.setDatumDo(mDatumDo.getText().toString());
        res.setBoja(mDefaultColor);
        AppManager.getInstance().saveReservation(AddReservationActivity.this,res);
    }

    private void openColorPicker() {
        AmbilWarnaDialog colorPicker=new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                    mDefaultColor=color;
                    mViewDisplayColor.setBackgroundColor(mDefaultColor);
            }
        });
        colorPicker.show();
    }

    @Override
    public void onReservationSaved(boolean saved) {
        if(saved) {
            Toast.makeText(AddReservationActivity.this, "Rezervacija sacuvana.", Toast.LENGTH_LONG).show();
            closeActivityThread.start();
        }
        else
            Toast.makeText(AddReservationActivity.this,"Doslo je do greske. Pokusajte kasnije.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReservationsReceived(ArrayList<Reservation> reservations) {

    }
}