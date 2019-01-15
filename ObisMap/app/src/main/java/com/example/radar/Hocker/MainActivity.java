package com.example.radar.Hocker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.*;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener  {
    //Appelemente
    private TextView textInfo;
    private TextView textEdit;
    private Button buttonSend;
    private Button buttonReceive;
    private static GoogleMap mMap;
    private Context context;
    private LocationManager mLocationManager;
    private static LinkedList<Marker> markerList = new LinkedList<>();


    //Parameter für lon und lat
    double longitude, latitude;

    //Gestenerkennung
    // accelerometer erzeugen
    private Accelerometer accelerometer;
    // gyroscope erzeugen
    private Gyroscope gyroscope;
    // boolean Handy ist ausgangsposition
    boolean xNormal = true;
    // boolean handy nach hinten gekippt
    boolean xBack = false;
    // boolean handy nach links gedreht
    boolean zLeft = false;
    // boolean handy nach rechts gedreht
    boolean zRight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = getApplicationContext();
        Database d = new Database();
        textInfo = (TextView) findViewById(R.id.textInfo);
        textEdit = (TextView) findViewById(R.id.textEdit);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonReceive = (Button) findViewById(R.id.buttonReceive);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        textInfo.setText("Herzlich Willkommen. Bitte zuerst auf das kleine Ortungssymbol oben rechts auf der Karte klicken um eine möglichst genaue Ortung zu erzielen, damit die App richtig funktioniert. Außerdem Internet und GPS aktivieren.");
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = getLastKnownLocation();
                if (location == null) {
                    textInfo.setText("Es konnte keine Postion gefunden werden. Bitte sichergehen, dass GPS aktiviert ist und am Besten einmal in der Google Maps App orten lassen.");
                    return;
                }
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                // textInfo.setText(latitude + " , " + longitude);
                String eingabe = textEdit.getText().toString();
                int ret = d.insertPositionWithText(longitude, latitude, eingabe);
                switch (ret) {
                    case 0:
                        textInfo.setText("Eintrag " + eingabe + " erfolgreich hochgeladen.");
                        break;
                    case 1:
                        textInfo.setText("Fehler beim Aufbau der Verbindung. Bitte prüfen ob eine stabile Verbindung zum Internet besteht.");
                        break;
                    case 2:
                        textInfo.setText("Fehler beim Hochladen der Daten. Bitte erneut versuchen. ");
                }
            }
        });

        buttonReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String erg = d.getData();
                textInfo.setText(erg);
            }
        });
        textEdit.setVisibility(View.INVISIBLE);
        buttonReceive.setVisibility(View.INVISIBLE);
        buttonSend.setVisibility(View.INVISIBLE);

        //Gestenerkkung

        //Konstruktor
        accelerometer = new Accelerometer(this);
        //Konstruktor
        gyroscope = new Gyroscope(this);
        //Listener an Accelerometer binden
        accelerometer.setListerner(new Accelerometer.Listener() {
            @Override
            //bei lineare Handbewegung

            public void onTranslation(float tx, float ty, float tz) {
                //Handy ausgangsposition
                if(xNormal)
                {
                    //aufwärts
                    if(tz > 11.0f)
<<<<<<< HEAD
                    {
                        String message = "" + textEdit.getText().toString();
                        if(!message.equals("")) {
                            Location location = getLastKnownLocation();
                            if (location == null) {
                                textInfo.setText("Es konnte keine Postion gefunden werden. Bitte sichergehen, dass GPS aktiviert ist und am Besten einmal in der Google Maps App orten lassen.");
                                return;
                            }
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            // textInfo.setText(latitude + " , " + longitude);
                            String eingabe = textEdit.getText().toString();
                            int ret = d.insertPositionWithText(longitude, latitude, eingabe);
                            switch (ret) {
                                case 0:
                                    textInfo.setText("Eintrag " + eingabe + " erfolgreich hochgeladen.");
                                    break;
                                case 1:
                                    textInfo.setText("Fehler beim Aufbau der Verbindung. Bitte prüfen ob eine stabile Verbindung zum Internet besteht.");
                                    break;
                                case 2:
                                    textInfo.setText("Fehler beim Hochladen der Daten. Bitte erneut versuchen. ");
                            }
=======
                    {   
                        Location location = getLastKnownLocation();
                        if (location == null) {
                            textInfo.setText("Es konnte keine Postion gefunden werden. Bitte sichergehen, dass GPS aktiviert ist und am Besten einmal in der Google Maps App orten lassen.");
                            return;
                        }
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        // textInfo.setText(latitude + " , " + longitude);
                        String eingabe = textEdit.getText().toString();
                        int ret = d.insertPositionWithText(longitude, latitude, eingabe);
                        switch (ret) {
                            case 0:
                                textInfo.setText("Eintrag " + eingabe + " erfolgreich hochgeladen.");
                                break;
                            case 1:
                                textInfo.setText("Fehler beim Aufbau der Verbindung. Bitte prüfen ob eine stabile Verbindung zum Internet besteht.");
                                break;
                            case 2:
                                textInfo.setText("Fehler beim Hochladen der Daten. Bitte erneut versuchen. ");
>>>>>>> 7c87b8f3f1cf561d6c9ca1c68e10ed6bbc2a627b
                        }
                    }
                    //abwärts
                    else if (tz < -9.0f)
                    {
                        String erg = d.getData();
                        textInfo.setText(erg);
                    }
                }
                //handy nach hinten gelehnt
                else if(xBack)
                {
                    //aufwärts
                    if(ty > 11.0f)
                    {
                        String message = "" + textEdit.getText().toString();
                        if(!message.equals("")) {
                            Location location = getLastKnownLocation();
                            if (location == null) {
                                textInfo.setText("Es konnte keine Postion gefunden werden. Bitte sichergehen, dass GPS aktiviert ist und am Besten einmal in der Google Maps App orten lassen.");
                                return;
                            }
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            // textInfo.setText(latitude + " , " + longitude);
                            String eingabe = textEdit.getText().toString();
                            int ret = d.insertPositionWithText(longitude, latitude, eingabe);
                            switch (ret) {
                                case 0:
                                    textInfo.setText("Eintrag " + eingabe + " erfolgreich hochgeladen.");
                                    break;
                                case 1:
                                    textInfo.setText("Fehler beim Aufbau der Verbindung. Bitte prüfen ob eine stabile Verbindung zum Internet besteht.");
                                    break;
                                case 2:
                                    textInfo.setText("Fehler beim Hochladen der Daten. Bitte erneut versuchen. ");
                            }
                        }
                    }
                    //abwärts
                    else if (ty < -9.0f)
                    {
                        String erg = d.getData();
                        textInfo.setText(erg);
                    }
                }
                //handy nach links gedreht
                else if (zLeft){
                    //aufwärts
                    if(tx > 11.0f)
                    {
                        String message = "" + textEdit.getText().toString();
                        if(!message.equals("")) {
                            Location location = getLastKnownLocation();
                            if (location == null) {
                                textInfo.setText("Es konnte keine Postion gefunden werden. Bitte sichergehen, dass GPS aktiviert ist und am Besten einmal in der Google Maps App orten lassen.");
                                return;
                            }
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            // textInfo.setText(latitude + " , " + longitude);
                            String eingabe = textEdit.getText().toString();
                            int ret = d.insertPositionWithText(longitude, latitude, eingabe);
                            switch (ret) {
                                case 0:
                                    textInfo.setText("Eintrag " + eingabe + " erfolgreich hochgeladen.");
                                    break;
                                case 1:
                                    textInfo.setText("Fehler beim Aufbau der Verbindung. Bitte prüfen ob eine stabile Verbindung zum Internet besteht.");
                                    break;
                                case 2:
                                    textInfo.setText("Fehler beim Hochladen der Daten. Bitte erneut versuchen. ");
                            }
                        }
                    }
                    //abwärts
                    else if (tx < -9.0f)
                    {
                        String erg = d.getData();
                        textInfo.setText(erg);
                    }
                }
                //handy nach rechts gedreht
                else if (zRight){
                    //abwärts
                    if(tx > -9.0f)
                    {
                        String message = "" + textEdit.getText().toString();
                        if(!message.equals("")) {
                            Location location = getLastKnownLocation();
                            if (location == null) {
                                textInfo.setText("Es konnte keine Postion gefunden werden. Bitte sichergehen, dass GPS aktiviert ist und am Besten einmal in der Google Maps App orten lassen.");
                                return;
                            }
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            // textInfo.setText(latitude + " , " + longitude);
                            String eingabe = textEdit.getText().toString();
                            int ret = d.insertPositionWithText(longitude, latitude, eingabe);
                            switch (ret) {
                                case 0:
                                    textInfo.setText("Eintrag " + eingabe + " erfolgreich hochgeladen.");
                                    break;
                                case 1:
                                    textInfo.setText("Fehler beim Aufbau der Verbindung. Bitte prüfen ob eine stabile Verbindung zum Internet besteht.");
                                    break;
                                case 2:
                                    textInfo.setText("Fehler beim Hochladen der Daten. Bitte erneut versuchen. ");
                            }
                        }
                    }
                    //aufwärts
                    else if (tx < 11.0f)
                    {
                        String erg = d.getData();
                        textInfo.setText(erg);
                    }
                }
            }
        });
        //Listener an Gyroscope binden
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            //Bei Handy Rotation
            public void onRotation(float rx, float ry, float rz) {
                //Handy ausgangsposition
                if(rx > 4.5f){
                    xNormal = true;
                    xBack = false;
                }
                //Handy wird nach hinten gelehnt
                else if (rx < -4.5f) {
                    xBack = false;
                    xNormal = true;
                }
                //Handy nach links gedreht
                if(rz > 4.5f){
                    if(zLeft == false){
                        zRight = true;
                    }else {
                        zRight=false;
                        zLeft=false;
                    }
                }
                //Handy nach rechts gedreht
                else if (rz < -4.5f) {
                    if(zRight==false){
                        zLeft = true;
                    }else {
                        zRight=false;
                        zLeft=false;
                    }
                }
            }
        });
    }

    @Override
    //Wird App ausgeführt, werden acc und gyro angemeldet
    protected void onResume(){
        super.onResume();

        accelerometer.register();
        gyroscope.register();
    }

    @Override
    //wird App pausiert, werden acc und gyro pausiert
    protected void onPause() {
        super.onPause();

        accelerometer.unregister();
        gyroscope.unregister();
    }



    /**
     * Ermittelt die letzte bekannte Postition
     * @return ermittelte Position
     */
    private Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET
                }, 10);
            }
            textInfo.setText("Um die App zu verwenden muss die Position ermittelt werden können. Daher ditte die Berechtigungen zulassen. Anschließend bitte erneut auf Senden drücken.");
            return null;
        }
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);

        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {

                bestLocation = l;
            }
        }
        return bestLocation;
    }
    @Override
    /**
     * Lädt Map
     */
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //für den Compiler ... nicht nötig da hier zu 100% Berechtigungen vorhanden sind
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET
                }, 10);
            }
            textInfo.setText("Um die App zu verwenden muss die Position ermittelt werden können. Daher ditte die Berechtigungen zulassen. Anschließend bitte erneut auf Senden drücken.");
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this::onMyLocationButtonClick);

    }

    /**
     * Fügt Marker auf Map hinzu
     * @param lat
     * @param lon
     * @param text
     */
    public static void addMarker(double lat, double lon, String text) {
        LatLng point = new LatLng(lat, lon);
        Marker m= mMap.addMarker(new MarkerOptions().position(point).title(text));
        markerList.add(markerList.size(), m);

    }
    public static void removeAllMarker() {
        for (int i=0; i < markerList.size(); i++) {
            markerList.get(i).remove();
        }
        markerList = new LinkedList<>();
    }
    @Override
    /**
     * Wenn auf Ortung oben rechts geklickt wird setzt er die Felder auf sichtbar
     */
    public boolean onMyLocationButtonClick() {

        Location l = getLastKnownLocation();
        //Location l = getDeviceLocation();
        if (l != null) {
            longitude = l.getLongitude();
            latitude = l.getLatitude();
            LatLng ort = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ort));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ort, 3));
            textEdit.setVisibility(View.VISIBLE);
            buttonReceive.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.VISIBLE);
        }
        else {
            textInfo.setText("Es konnte keine Postion gefunden werden. Bitte sichergehen, dass GPS aktiviert ist und am Besten einmal in der Google Maps App orten lassen.");
        }
        return false;
    }

    /**
     * Prüft ob Berechtigungen da sind
     * @return
     */
    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                )
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET
                }, 10);
            }
            return false;
        }
        return true;
    }
}
