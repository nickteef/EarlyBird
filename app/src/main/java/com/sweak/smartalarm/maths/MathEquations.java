package com.sweak.smartalarm.maths;

import android.os.Bundle;

import com.sweak.smartalarm.R;
import com.sweak.smartalarm.util.Preferences;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MathEquations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDismissKeyguard();

        setContentView(R.layout.activity_math_equations);
        GenerateMathsProblem();


        Button btn =  findViewById(R.id.generate_equation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenerateMathsProblem();
            }
        });

        Button btn2 =  findViewById(R.id.button_submit);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });
    }

    private void setDismissKeyguard() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }
    public void GenerateMathsProblem(){
        TextView tv1 = (TextView)findViewById(R.id.text_equation);
        tv1.setText(generateProblem());
    }

    public static String generateProblem() {
        Random rand = new Random();
        int x = rand.nextInt(100); // Generates a random number between 0 and 9
        int y = rand.nextInt(100);
        int operation = rand.nextInt(4); // Generates a random number between 0 and 3
        int z = 0;
        String symbol = "";
        switch (operation) {
            case 0:
                z = x + y;
                symbol = "+";
                break;
            case 1:
                z = x - y;
                symbol = "-";
                break;
            case 2:
                z = x * y;
                symbol = "*";
                break;
            case 3:
                z = x / y;
                symbol = "/";
                break;
        }
        return x + " " + symbol + " " + y + " = ";
    }

    public void checkAnswer() {
        TextView tv1 = (TextView)findViewById(R.id.text_equation);
        String problem = tv1.getText().toString();
        EditText et1 = (EditText)findViewById(R.id.user_anwser);
        TextView tv2 = (TextView)findViewById(R.id.textView_true_false);


        if (et1.getText().toString().equals("")){
            tv2.setText("Napaka pri vnosu");
        }else{
            int answer = Integer.valueOf(et1.getText().toString());
            // Split the problem into its component parts
            String[] parts = problem.split(" ");
            int x = Integer.parseInt(parts[0]);
            String symbol = parts[1];
            int y = Integer.parseInt(parts[2]);
//            int z = Integer.parseInt(parts[3]);
            System.out.println(x + symbol + y + " = " + answer);
            switch (symbol) {
                case "+":
                    if(x + y == answer){
                        tv2.setText("Pravilno");
                        GenerateMathsProblem();
                    }else{
                        tv2.setText("Napačno");
                    } break;
                case "-":
                    if(x - y == answer){
                        tv2.setText("Pravilno");
                        GenerateMathsProblem();
                    }else{
                        tv2.setText("Napačno");
                    } break;
                case "*":
                    if(x * y == answer){
                        tv2.setText("Pravilno");
                        GenerateMathsProblem();
                    }else{
                        tv2.setText("Napačno");
                    } break;
                case "/":
                    if(x / y == answer){
                        tv2.setText("Pravilno");
                        GenerateMathsProblem();
                    }else{
                        tv2.setText("Napačno");
                    } break;
                default:
                    tv2.setText("lala");
            }
        }
    }
}