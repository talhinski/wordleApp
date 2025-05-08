package com.hinski.wordelapplication.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginViewModel extends AndroidViewModel {

    private final FirebaseAuth auth;
    public String email = "";
    public String password = "";

    private final MutableLiveData<Boolean> navigateToRegister = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void login() {
        if (validateInput()) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                saveUserDetails(user);
                            }
                            loginSuccess.setValue(true);
                            Toast.makeText(getApplication(), "Login successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(), "Login failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean validateInput() {
        boolean isValid = true;

        if (email.isEmpty()) {
            Toast.makeText(getApplication(), "Email is required", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (password.isEmpty()) {
            Toast.makeText(getApplication(), "Password is required", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void saveUserDetails(FirebaseUser user) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", user.getUid());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_name", user.getDisplayName());
        editor.apply();
    }

    public void navigateToRegister() {
        navigateToRegister.setValue(true);
    }

    public MutableLiveData<Boolean> getNavigateToRegister() {
        return navigateToRegister;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }
}