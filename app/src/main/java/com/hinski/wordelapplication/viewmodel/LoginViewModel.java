package com.hinski.wordelapplication.viewmodel;

import android.app.Application;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends AndroidViewModel {

    private final FirebaseAuth auth;
    public String email = "";
    public String password = "";

    private final MutableLiveData<Boolean> navigateToRegister = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void login() {
        if (validateInput()) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplication(), "Login successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

    public void navigateToRegister() {
        navigateToRegister.setValue(true);
    }

    public MutableLiveData<Boolean> getNavigateToRegister() {
        return navigateToRegister;
    }
}