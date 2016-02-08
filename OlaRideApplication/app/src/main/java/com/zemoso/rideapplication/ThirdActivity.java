package com.zemoso.rideapplication;

import android.Manifest;
import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class ThirdActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{


    SharedPreferences sharedPreferences;
    TextView headerText;
    TextView subHeaderText;
    private GoogleMap mMap;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    EditText loca;
    MYSQLiteHelper db;
    Address currentAddress ;
  boolean isSearching = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_layout);
         db = new MYSQLiteHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //to assign the dynamic value to header like the firstname entered or mobile number
        //the header view will need to be inflated in the Third Activity
        //Make sure to remove the line which tells the navigation view in third_layout file about navigation_header as otherwise it will shown twice
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_third);//http://stackoverflow.com/questions/33560219/in-android-how-to-set-navigation-drawer-header-image-and-name-programmatically-i
        headerText = (TextView) headerView.findViewById(R.id.headerText);
        subHeaderText = (TextView) headerView.findViewById(R.id.subHeaderText);

        sharedPreferences = getSharedPreferences(MainActivity.MYPREFERENCES, Context.MODE_PRIVATE);

        headerText.setText(sharedPreferences.getString(MainActivity.firstNameKey, "Android Studio"));
        subHeaderText.setText(sharedPreferences.getString(MainActivity.mobileNumberKey,"android"));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.third, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent = new Intent(this, ThirdActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, MyRides.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this,RateCard.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow1) {
            Intent intent = new Intent(this,OffersActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(this,FreeRides.class);
            startActivity(intent);
        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       setUpMap();
    }


    private void setUpMap() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged (Location location)


            {
                if(!isSearching) {
                    showCurrentLocation(location);
                }

            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 2000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            showCurrentLocation(location);
        }
    }

    private void showCurrentLocation(Location location){
        List<Address> addressList = null;
        if(location != null) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(addressList!=null && !addressList.isEmpty()) {
            currentAddress = addressList.get(0);
            mMap.clear();
            LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 18));
        }
        else{
            if(!isNetworkAvailable(this)) {
                Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
                finish(); //Calling this method to close this activity when internet is not available.
            }
        }
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
    public void onSearch(View view) {


         loca = (EditText) findViewById(R.id.editText);
        String location1  =  loca.getText().toString();
        List<Address> addressList = null;

        if

            (location1 != null && !location1.equals("")) {
            isSearching = true;
            Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location1, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addressList!=null&&!addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    currentAddress = address;
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }

            }

        else{
            setUpMap();
        }

    }

    public void changeType (View view){
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else{
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }


    public void clickOnRideNow(View view){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(ThirdActivity.this);
        builder1.setMessage("Your Ride has been booked");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addToDatabase();
                        dialog.cancel();


                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }


    private void addToDatabase(){
        Display userDetails = new Display();
        userDetails.setMobilenumber(sharedPreferences.getString(MainActivity.mobileNumberKey, "android"));
        userDetails.setCabtype("Mini");
        userDetails.setRidedistance("50km");
        userDetails.setUsername(sharedPreferences.getString(MainActivity.firstNameKey, "android"));
        userDetails.setTimetaken("20 mins");
        userDetails.setWaitingtime("5min");
        String address = "No Address";
        if(currentAddress != null){
            if (currentAddress.getAddressLine(0) != null) {
                address = currentAddress.getAddressLine(0);
            }
            if (currentAddress.getAddressLine(1) != null) {
                address = address + " " + currentAddress.getAddressLine(1);
            }
            if (currentAddress.getAddressLine(2) != null) {
                address = address + " " + currentAddress.getAddressLine(2);
            }
            if (currentAddress.getAddressLine(3) != null) {
                address = address + " " + currentAddress.getAddressLine(3);
            }
        }
        userDetails.setStartingplace(address);
        userDetails.setBoookingTime(new Timestamp(System.currentTimeMillis()));//Time stamp is used to set date time
        db.addDisplay(userDetails);










        }

    };





