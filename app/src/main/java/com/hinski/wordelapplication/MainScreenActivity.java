package com.hinski.wordelapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("user_email", null);

        if (userName == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            TextView greetingTextView = findViewById(R.id.greeting_text_view);
            greetingTextView.setText("Welcome, " + userName);
        }

        this.findViewById(R.id.button_new_game).setOnClickListener(v -> {
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

        Button logoutButton = findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}