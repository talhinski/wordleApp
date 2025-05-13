package com.hinski.wordelapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hinski.wordelapplication.databinding.ActivityMainScreenBinding;
import com.hinski.wordelapplication.viewmodel.MainScreenViewModel;

public class MainScreenActivity extends AppCompatActivity {

    private MainScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainScreenBinding binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(MainScreenViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.navigateToLogin.observe(this, navigate -> {
            if (navigate) {
                Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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

        // Observe logout confirmation event
        viewModel.showLogoutConfirmation.observe(this, this::showLogoutDialog);

        // Request notification permission for Android 13+
        WordelApplication.requestNotificationPermission(this);
    }

    private void showLogoutDialog(Boolean show) {
        if (Boolean.TRUE.equals(show)) {
            new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> viewModel.logout())
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }
}

