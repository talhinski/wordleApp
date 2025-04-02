package com.hinski.wordelapplication.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainScreenViewModel extends AndroidViewModel {

    private final SharedPreferences sharedPreferences;
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<Boolean> navigateToLogin = new MutableLiveData<>();
    public MutableLiveData<Boolean> navigateToRegister = new MutableLiveData<>();

    public MutableLiveData<Boolean> startGame = new MutableLiveData<>();

    public MainScreenViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        loadUserName();
    }

    private void loadUserName() {
        String name = sharedPreferences.getString("user_name", null);
        if (name == null) {
            navigateToLogin.setValue(true);
        } else {
            userName.setValue(name);
        }
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        navigateToLogin.setValue(true);
    }

    public void register() {
        navigateToRegister.setValue(true);
    }
    public void startNewGame() {
        startGame.setValue(true);
    }

}