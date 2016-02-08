package com.zemoso.rideapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MyRides extends AppCompatActivity {
    TextView textView;
    MYSQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides);
       // ArrayList<Display> displays = GetDisplays();
        final ListView L1 = (ListView) findViewById(R.id.ListView01);
        db = new MYSQLiteHelper(this);
        ArrayList<Display> users = (ArrayList<Display>) db.getAllDisplay();
        Log.d("Arraylist Size :",String.valueOf(users.size()));
        L1.setAdapter(new MyCustomBaseAdapter(this, users));
        L1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = L1.getItemAtPosition(position);
                Display fullObject = (Display) object;
                Toast.makeText(MyRides.this, "You have chosen: " + " " + fullObject.getUsername(), Toast.LENGTH_LONG).show();
            }
        });

       // textView = (TextView) findViewById(R.id.textView7);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // ;



    }
    //List is created

    private ArrayList<Display>GetDisplays(){
        ArrayList<Display> results = new ArrayList<Display>();
        Display D1 = new Display();
        D1.setUsername("him");
        D1.setRidedistance("9958798915");
        D1.setStartingplace("3 sector");
        D1.setRidedistance("40 km");
        D1.setMobilenumber("9971272843");
        D1.setStartingplace("gachibowli");
        D1.setRidedistance("20km");
        D1.setTimetaken("1 hour");
        D1.setWaitingtime("20 minutes");
        D1.setCabtype("sedan");
        results.add(D1);


        Display D5 = new Display();
        D5.setUsername("swati");
        D5.setMobilenumber("7674980013");
        D5.setRidedistance("18km");
        D5.setStartingplace("dlf");
        D5.setTimetaken("55 minutes");
        D5.setWaitingtime("4 mins");
        D5.setCabtype("sedan");
        results.add(D5);

        Display D2 = new Display();
        D2.setUsername("Asha");
        D2.setMobilenumber("9560927214");
        D2.setRidedistance("20 km");
        D2.setStartingplace("faridabad");
        D2.setTimetaken("50 minutes");
        D2.setWaitingtime("10 minutes");
        D2.setCabtype("sedan");
        results.add(D2);

        Display D3 = new Display();
        D3.setUsername("Rajesh");
        D3.setMobilenumber("9958798915");
        D3.setRidedistance("22 km");
        D3.setStartingplace("delhi");
        D3.setTimetaken("2 hours");
        D3.setWaitingtime("2 minutes");
        D3.setCabtype("sedan");
        results.add(D3);

      Display D4 = new Display();
        D4.setUsername("sagar");
        D4.setMobilenumber("9560927214");
        D4.setRidedistance("10 km");
        D4.setStartingplace("gurgaon");
        D4.setTimetaken("30 minutes");
        D4.setWaitingtime("9 minutes");
        D4.setCabtype("sedan");
        return results;
    }


        }































