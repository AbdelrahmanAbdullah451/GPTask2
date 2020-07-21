package com.example.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    public boolean flag = false;
    private TextView xAccTxt , yAccTxt , zAccTxt , xGyroTxt , yGyroTxt , zGyroTxt ;
    private Sensor accelerometerSensor , gyroscopeSensor;
    private SensorManager SM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sBtn = findViewById(R.id.startBtn);
        Button endBtn = findViewById(R.id.stopBtn);


        // Create out sensor maneger
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer sensor
        accelerometerSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Gyroscope sensor
        gyroscopeSensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Register Accelerometer sensor listener
        SM.registerListener(this, accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);

        // Register Gyroscope sensor listener
        SM.registerListener(this , gyroscopeSensor , SensorManager.SENSOR_DELAY_NORMAL);


        // Assign TextView
        xAccTxt = (TextView)findViewById(R.id.xAcc);
        yAccTxt = (TextView)findViewById(R.id.yAcc);
        zAccTxt = (TextView)findViewById(R.id.zAcc);
        xGyroTxt = (TextView)findViewById(R.id.xGyro);
        yGyroTxt = (TextView)findViewById(R.id.yGyro);
        zGyroTxt = (TextView)findViewById(R.id.zGyro);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        while(flag){
            // start writing into file
        }

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            xAccTxt.setText("X : " + sensorEvent.values[0]);
            yAccTxt.setText("Z : " + sensorEvent.values[1]);
            zAccTxt.setText("Z : " + sensorEvent.values[2]);
        }else if(sensor.getType() == Sensor.TYPE_GYROSCOPE){
            xGyroTxt.setText("X : " + sensorEvent.values[0]);
            yGyroTxt.setText("Z : " + sensorEvent.values[1]);
            zGyroTxt.setText("Z : " + sensorEvent.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // not in use
    }

    public void startRecording(View view) {
        // clean the file
        flag = true;
        //
    }

    public void stopRecording(View view) {
        flag = false ;
        // send the data to backend
    }
}