package com.hinski.wordelapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hinski.wordelapplication.databinding.ActivityMainScreenBinding;
import com.hinski.wordelapplication.viewmodel.MainScreenViewModel;

public class MainScreenActivity extends AppCompatActivity {

    private ActivityMainScreenBinding binding;
    private MainScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    }
}