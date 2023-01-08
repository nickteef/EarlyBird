package com.sweak.smartalarm.features.main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sweak.smartalarm.R;
import com.sweak.smartalarm.features.menu.MenuActivity;
import com.sweak.smartalarm.features.stepcount.StepCounter;
import com.sweak.smartalarm.maths.MathEquations;

public class MainScreen extends AppCompatActivity {

    Button toStep, toAlarm, toSettings, toMath;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        toStep = findViewById(R.id.go_to_doggy);
        toSettings = findViewById(R.id.go_to_settings);
        toAlarm = findViewById(R.id.go_to_alarm);
        toMath = findViewById(R.id.go_to_math);

        toStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStepCounterActivity();
            }
        });
        toMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMathEquationsActivity();
            }
        });
        toSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMenuActivity();
            }
        });
        toAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlarmActivity();
            }
        });

    }
    private void startStepCounterActivity() {
        Intent intent = new Intent(this, StepCounter.class);
        startActivity(intent);
    }
    private void startMathEquationsActivity() {
        Intent intent = new Intent(this, MathEquations.class);
        startActivity(intent);
    }
    private void startMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    private void startAlarmActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}