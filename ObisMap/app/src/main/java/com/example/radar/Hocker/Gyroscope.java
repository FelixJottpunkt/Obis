package com.example.radar.Hocker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


public class Gyroscope {

    public interface Listener
    {
        void onRotation(float rx, float ry, float rz);
    }

    private Listener listerner;

    public void setListener(Listener l)
    {
        listerner = l;
    }

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    Gyroscope(Context context)
    {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listerner != null)
                {
                    //Listener updatet X Y Z , wenn sich das Gyroscope verändert
                    listerner.onRotation(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    //bindet Listener an Sensor an
    public void register()
    {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //entfernt Listener vom Sensor
    public void unregister()
    {
        sensorManager.unregisterListener(sensorEventListener);
    }
}

