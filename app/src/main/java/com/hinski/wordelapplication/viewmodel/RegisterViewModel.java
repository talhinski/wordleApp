package com.hinski.wordelapplication.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterViewModel extends AndroidViewModel {

    private final FirebaseAuth auth;
    private final FirebaseFirestore db;
    public String email = "";
    public String password = "";
    public String confirmPassword = "";
    public String userName = "";

    public MutableLiveData<String> emailError = new MutableLiveData<>();
    public MutableLiveData<String> passwordError = new MutableLiveData<>();
    public MutableLiveData<String> confirmPasswordError = new MutableLiveData<>();
    public MutableLiveData<String> userNameError = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void register() {
        if (validateInput()) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                saveUserToFirestore(user);
                                user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(userName).build());
                                auth.updateCurrentUser(user);
                            }
                            Toast.makeText(getApplication(), "Registration successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private boolean validateInput() {
        AtomicBoolean isValid = new AtomicBoolean(true);

        if (TextUtils.isEmpty(email)) {
            emailError.setValue("Email is required");
            isValid.set(false);
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError.setValue("Invalid email address");
            isValid.set(false);
        } else {
            //noinspection deprecation
            auth.fetchSignInMethodsForEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().getSignInMethods().isEmpty()) {
                            emailError.setValue("Email already in use");
                            isValid.set(false);
                        } else {
                            emailError.setValue(null);
                        }
                    });
        }


        if (TextUtils.isEmpty(password)) {
            passwordError.setValue("Password is required");
            isValid.set(false);
        } else if (password.length() < 6) {
            passwordError.setValue("Password must be at least 6 characters");
            isValid.set(false);
        } else {
            passwordError.setValue(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordError.setValue("Confirm Password is required");
            isValid.set(false);
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordError.setValue("Passwords do not match");
            isValid.set(false);
        } else {
            confirmPasswordError.setValue(null);
        }

        if (TextUtils.isEmpty(userName)) {
            userNameError.setValue("User Name is required");
            isValid.set(false);
        } else {
            userNameError.setValue(null);
        }

        return isValid.get();
    }

    private void saveUserToFirestore(FirebaseUser user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userName", userName);
        userMap.put("email", user.getEmail());

        db.collection("users").document(user.getUid())
                .set(userMap)
                .addOnSuccessListener(aVoid -> Toast.makeText(getApplication(), "User saved", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getApplication(), "Error saving user: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}