package com.example.calculatorapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    boolean isGreeting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        TextView helloText = findViewById(R.id.helloText);
        Button changeButton = findViewById(R.id.changeButton);



        changeButton.setOnClickListener(v -> {
            if (isGreeting) {
                helloText.setText("Hello World");
                isGreeting = false;
            }
            else{
                helloText.setText("Hello Elliott");
                isGreeting = true;
            }

        });




            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}