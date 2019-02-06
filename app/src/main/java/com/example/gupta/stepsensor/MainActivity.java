package com.example.gupta.stepsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sman;
    Sensor stepsensor;
    private long steps=0;
    TextView t1;
    float distance=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1=findViewById(R.id.t1);
        sman=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepsensor= sman.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor=sensorEvent.sensor;
        float[] values=sensorEvent.values;
        int value=-1;
        for(int i=0;i<values.length;i++)
        Log.e(value+"i",values[i]+"");
        if (values.length>0){
            value=(int) values[0];
        }
        if(sensor.getType()==Sensor.TYPE_STEP_DETECTOR){
            steps++;
            t1.setText("steps= "+steps+"\ndiatance= "+getDistanceRun(steps));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float getDistanceRun(long steps){//in kms
        //Accurate:stepLength=(ht in cm)*(0.415 for men, 0.413 for women)
        return (float)(steps*70)/(float)100000;//78 for men, 70 for women
    }

    @Override
    protected void onResume() {
        super.onResume();
        sman.registerListener(this,stepsensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sman.unregisterListener(this,stepsensor);
    }
}
