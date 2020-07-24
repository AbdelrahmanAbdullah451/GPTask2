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


    public boolean flag = false;

    private static final String FILENAME = "accData.txt";
    private TextView xAccTxt, yAccTxt, zAccTxt, prompt;
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
        prompt = (TextView) findViewById(R.id.prompt);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;



        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAccTxt.setText("X : " + sensorEvent.values[0]);
            yAccTxt.setText("Z : " + sensorEvent.values[1]);
            zAccTxt.setText("Z : " + sensorEvent.values[2]);

            if (this.flag) {
                Point point;
                // assign values to db
                try{

                    point = new Point(-1,sensorEvent.values[0],sensorEvent.values[1] , sensorEvent.values[2],false);

                    Log.d("Object is : " , String.valueOf(point));
                }catch (Exception e){
                    Toast.makeText(this, "Error making the object", Toast.LENGTH_SHORT).show();
                    point = new Point(-1,0,0 , 0,false);
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success  = dataBaseHelper.addRecord(point);
            }
            else
            {

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // not in use
    }

    public void startRecording(View view) {
        this.flag = true;
        prompt.setText("Capturing stream ...");
    }

    public void stopRecording(View view) {
        this.flag = false;
        Point point;
        point = new Point(-1,0,0 , 0,true);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        boolean success  = dataBaseHelper.addRecord(point);
        prompt.setText("");
        Toast.makeText(this, "Stream Captured Successfully", Toast.LENGTH_SHORT).show();

    }
}