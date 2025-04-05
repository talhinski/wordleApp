package com.hinski.wordelapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hinski.wordelapplication.databinding.ActivityMainScreenBinding;
import com.hinski.wordelapplication.viewmodel.MainScreenViewModel;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainScreenBinding binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainScreenViewModel viewModel = new ViewModelProvider(this).get(MainScreenViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.navigateToLogin.observe(this, navigate -> {
            if (navigate) {
                Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewModel.navigateToRegister.observe(this, navigate -> {
            if (navigate) {
                Intent intent = new Intent(MainScreenActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        viewModel.startGame.observe(this, navigate -> {
            if (navigate) {
                Intent intent = new Intent(MainScreenActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        binding.buttonStatistics.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreenActivity.this, StatisticActivity.class);
            startActivity(intent);
        });

        // Request notification permission for Android 13+
        WordelApplication.requestNotificationPermission(this);
    }
}

