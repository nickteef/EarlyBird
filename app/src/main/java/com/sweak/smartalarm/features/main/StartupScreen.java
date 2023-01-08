package com.sweak.smartalarm.features.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sweak.smartalarm.R;
import com.sweak.smartalarm.features.stepcount.StepCounter;

public class StartupScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;


    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_startup_screen);



        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //Hooks
        image = findViewById(R.id.image_startup);
        logo = findViewById(R.id.textView_startup);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartupScreen.this, MainScreen.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}