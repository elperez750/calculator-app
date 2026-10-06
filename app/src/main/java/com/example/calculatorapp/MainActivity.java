package com.example.calculatorapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    private StringBuilder expression = new StringBuilder();
    private TextView tvResult;

    private TextView tvExpression;
    private boolean showingIntro = true;






    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "onResume called");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "onPause called");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "onStop called");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "onDestroy called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "onCreate called");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        tvExpression = findViewById(R.id.tvExpression);


        int[] ids = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnDot, R.id.btnPlus, R.id.btnMinus, R.id.btnTimes, R.id.btnDiv,
                R.id.btnOpen, R.id.btnClose, R.id.btnEqual, R.id.btnAc, R.id.btnC
        };

        for (int id : ids) {
            findViewById(id).setOnClickListener(v -> {
                String key = ((Button) v).getText().toString();
                onKeyPressed(key);
                updateDisplay();
            });
        }



            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }


    private void onKeyPressed(String key) {
        if (showingIntro) {
            showingIntro = false;
            tvExpression.setText("");   // what text makes it "disappear"?
        }
        if (key.equals("AC")) {
            // TODO 1: empty the
            expression.setLength(0);
        } else if (key.equals("C")) {
            if (expression.length() > 0) {

                expression.setLength(expression.length()-1);
            }
        } else if (key.equals("=")) {
            // leave this empty for now, we'll do it later
        } else {
            expression.append(key);
        }

        // TODO 4: make the screen match the expression
    }




    private void updateDisplay() {
            if (expression.length() == 0) {
                tvResult.setText("0");
            } else {
                tvResult.setText(expression.toString());
            }
        }





}