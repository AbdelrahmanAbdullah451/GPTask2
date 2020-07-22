package com.example.helloworld;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ArrayList<Float> xAccData = new ArrayList<Float>();
    ArrayList<Float> yAccData = new ArrayList<Float>();
    ArrayList<Float> zAccData = new ArrayList<Float>();

    public boolean flag = false;
//    float [][][] accData;
//    float [][][] gyroData;

    private static final String FILENAME = "accData.txt";
    private TextView xAccTxt, yAccTxt, zAccTxt, xGyroTxt, yGyroTxt, zGyroTxt;
    private Sensor accelerometerSensor, gyroscopeSensor;
    private SensorManager SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sBtn = findViewById(R.id.startBtn);
        Button endBtn = findViewById(R.id.stopBtn);


        // Create out sensor maneger
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer sensor
        accelerometerSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Gyroscope sensor
        gyroscopeSensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Register Accelerometer sensor listener
        SM.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Register Gyroscope sensor listener
        SM.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);


        // Assign TextView
        xAccTxt = (TextView) findViewById(R.id.xAcc);
        yAccTxt = (TextView) findViewById(R.id.yAcc);
        zAccTxt = (TextView) findViewById(R.id.zAcc);
        xGyroTxt = (TextView) findViewById(R.id.xGyro);
        yGyroTxt = (TextView) findViewById(R.id.yGyro);
        zGyroTxt = (TextView) findViewById(R.id.zGyro);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;


        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAccTxt.setText("X : " + sensorEvent.values[0]);
            yAccTxt.setText("Z : " + sensorEvent.values[1]);
            zAccTxt.setText("Z : " + sensorEvent.values[2]);

            if (this.flag) {
                xAccData.add(sensorEvent.values[0]);
                yAccData.add(sensorEvent.values[1]);
                zAccData.add(sensorEvent.values[2]);
            }
        }


//        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
//            xAccTxt.setText("X : " + sensorEvent.values[0]);
//            yAccTxt.setText("Z : " + sensorEvent.values[1]);
//            zAccTxt.setText("Z : " + sensorEvent.values[2]);
//
//        }else if(sensor.getType() == Sensor.TYPE_GYROSCOPE){
//            xGyroTxt.setText("X : " + sensorEvent.values[0]);
//            yGyroTxt.setText("Z : " + sensorEvent.values[1]);
//            zGyroTxt.setText("Z : " + sensorEvent.values[2]);
//        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // not in use
    }

    public void startRecording(View view) {
        // clean the file
        xAccData.clear();
        yAccData.clear();
        zAccData.clear();
        this.flag = true;

    }

    public void stopRecording(View view) {
        this.flag = false;


    }

    // X , Y and Z values of accelerometer are stored in the three arraylists bellow :
    //           xAccData
    //           yAccData
    //           zAccData
}