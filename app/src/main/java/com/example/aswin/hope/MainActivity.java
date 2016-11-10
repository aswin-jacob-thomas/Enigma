package com.example.aswin.hope;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aswin.info.R;

public class MainActivity extends AppCompatActivity {

    public Sensor mySensor, mySensor2;
    public SensorManager SM,SM2;
    private Button start;
    private Button stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start=(Button)findViewById(R.id.start);


    }
    public void onClick(View v){
        Log.d("what is happ","Min");
        Intent i = new Intent(this,UpdateService.class);
        startService(i);
    }




}