package com.hinski.wordelapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_screen);
        this.findViewById(R.id.button_new_game).setOnClickListener(v -> {
            // Start a new game
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });

        Button registerButton = findViewById(R.id.button_register);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreenActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}