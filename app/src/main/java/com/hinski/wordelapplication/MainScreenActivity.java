package com.hinski.wordelapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

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

    }
}