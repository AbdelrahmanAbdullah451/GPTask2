package com.example.helloworld;


import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.InputStream;
import java.util.Properties;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    public boolean flag = false;
    private TextView xAccTxt , yAccTxt , zAccTxt , xGyroTxt , yGyroTxt , zGyroTxt ;
    private Sensor accelerometerSensor , gyroscopeSensor;
    private SensorManager SM;
    public static double XGLOP=99,YGLOP=58;
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
        //Log.d("Rhino", "stopRecording: "+ runScript(this));
    }

    public void stopRecording(View view) {
        flag = false ;
        //Log.d("Rhino", "stopRecording: "+ runScript(this));
        //Object[] functionParams = new Object[]{"cool"};

    }


    public static String runScript(Context androidContextObject) {
        // Get the JavaScript in previous section
        try {

            Resources resources = androidContextObject.getResources();
            InputStream rawResource = resources.openRawResource(R.raw.outlines_dollar_q);


            Properties properties = new Properties();
            properties.load(rawResource);

            String source = properties.getProperty("acc");
            String functionName = "point";
            Object[] functionParams = new Object[]{XGLOP,YGLOP,1};
            // Every Rhino VM begins with the enter()
            // This Context is not Android's Context
            org.mozilla.javascript.Context rhino;
            rhino = org.mozilla.javascript.Context.enter();

            // Turn off optimization to make Rhino Android compatible
            rhino.setOptimizationLevel(-1);

            Scriptable scope = rhino.initStandardObjects();

            // This line set the javaContext variable in JavaScript
            //ScriptableObject.putProperty(scope, "javaContext", org.mozilla.javascript.Context.javaToJS(androidContextObject, scope));

            // Note the forth argument is 1, which means the JavaScript source has
            // been compressed to only one line using something like YUI
            rhino.evaluateString(scope, source, "JavaScript", 1, null);

            // We get the hello function defined in JavaScript
            Object obj = scope.get(functionName, scope);

            if (obj instanceof Function) {
                Function function = (Function) obj;
                // Call the hello function with params
                Object result = function.call(rhino, scope, scope, functionParams);
                // After the hello function is invoked, you will see logcat output

                // Finally we want to print the result of hello function
                String response = org.mozilla.javascript.Context.toString(result);
                return response;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // We must exit the Rhino VM
            org.mozilla.javascript.Context.exit();
        }

        return null;
    }

}