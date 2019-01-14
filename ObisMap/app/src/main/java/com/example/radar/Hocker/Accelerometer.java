package com.example.radar.Hocker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Accelerometer {

    public interface Listener
    {
        void onTranslation(float tx, float ty, float tz);
    }

    private Listener listerner;

    public void setListerner(Listener l)
    {
        listerner = l;
    }

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    Accelerometer(Context context)
    {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listerner != null)
                {
                    //listener updatet X Y Z , wenn sich das Acc ändert
                    listerner.onTranslation(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    //bindet Listner and Sensor
    public void register()
    {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //entfernt Listner vom Sensor
    public void unregister()
    {
        sensorManager.unregisterListener(sensorEventListener);
    }
}
