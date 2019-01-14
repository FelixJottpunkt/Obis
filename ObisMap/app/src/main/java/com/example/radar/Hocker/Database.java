package com.example.radar.Hocker;

import android.os.Build;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.LinkedList;

public class Database {
    public Database() {
    }


    /**
     * Schreibt einen Punkt mit Text in eine Tabelle
     * @param lon Longitude
     * @param lat Latitude
     * @param text Text
     * @return
     */
    public int insertPositionWithText(double lon, double lat, String text) {
        Connection conn = null;
        //Verbindungsaufbau
        try {

            conn = DriverManager.getConnection(
                    "Database-URL", "Username","Password");
        } catch (SQLException e) {
           // StringWriter sw = new StringWriter();
           // e.printStackTrace(new PrintWriter(sw));
           // String exceptionAsString = sw.toString();
           // Log.i("Fehler" , "1" + exceptionAsString);
            return 1;
        }
        if (conn == null) {
            return 1;
        }
        //Punkt hochladen
        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO hocker(time, lat, lon, text) VALUES (?, ?, ?, ?)");
            pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            //andere Variablen falls Emulator
            if (lon < 0) {
                lat = Math.random() + 52;
                lon = Math.random() + 13;
                pst.setDouble(2, lat);
                pst.setDouble(3, lon);
            } else {
                pst.setDouble(2, lat);
                pst.setDouble(3, lon);
            }
            pst.setString(4,text);
            pst.executeUpdate();
           pst.close();
           conn.close();
        } catch (Exception e) {
            return 2;
        }
        return 0;


    }

    public String getData() {
        MainActivity.removeAllMarker();
        Connection conn = null;
        String result ="";
        try {

            conn = DriverManager.getConnection(
                   "Database-URL", "Username","Password");


        } catch (SQLException e) {
            return"Fehler beim Aufbau der Verbindung. Bitte prüfen ob eine stabile Verbindung zum Internet besteht.";
        }

        if (conn == null) {
            return "Fehler beim Aufbau der Verbindung. Bitte prüfen ob eine stabile Verbindung zum Internet besteht.";
        }


        int erg = 0;
        LinkedList<double[]> l = new LinkedList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT time, lat, lon, text FROM hocker");
            while (rs.next()) {
                double lat = rs.getDouble(2);
                double lon = rs.getDouble(3);
                String text = rs.getString(4);
                //Prüft ob Marker schon an der Position ist
                boolean markerPlaced = false;
                if (l.size() > 0) {
                    //Geht alle bisherigen Punkte durch um zu prüfen ob es welche an der Postion gibt
                    for (int i = 0; i < l.size(); i++) {
                        if (lat == l.get(i)[0] && lon==l.get(i)[1] && markerPlaced==false) {
                            MainActivity.addMarker(lat + (erg*0.00001), lon + (erg*0.00001), text);
                            l.add(l.size(), new double[]{lat + (erg*0.00001), lon + (erg*0.00001)});
                            markerPlaced = true;


                        }
                    }
                    if (markerPlaced==false) {
                        l.add(l.size(), new double[]{lat, lon});
                        MainActivity.addMarker(lat, lon, text);
                    }
                } else {
                    l.add(l.size(), new double[]{lat, lon});
                    MainActivity.addMarker(lat, lon, text);
                }


                erg++;
            }
            s.close();
            rs.close();
            conn.close();
        } catch (Exception e) {
            return "Fehler beim Abrufen der Daten. Bitte erneut versuchen. ";
        }
        return erg + " Datensätze erfolgreich abgerufen.";
    }
}
