package com.hinski.wordelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.hinski.wordelapplication.databinding.ActivityMainScreenBinding;
import com.hinski.wordelapplication.viewmodel.MainScreenViewModel;

public class MainScreenActivity extends AppCompatActivity {

    private ActivityMainScreenBinding binding;
    private MainScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());

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

                findViewById(R.id.button_statistics).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainScreenActivity.this, StatisticActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

