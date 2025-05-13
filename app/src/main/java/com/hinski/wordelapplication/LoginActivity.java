package com.hinski.wordelapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hinski.wordelapplication.databinding.ActivityLoginBinding;
import com.hinski.wordelapplication.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding; // קישור בין קובץ XML לקוד
    private LoginViewModel viewModel;     // לוגיקת התחברות (ה-ViewModel)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // מחבר בין קובץ activity_login.xml לקוד
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // יוצר את ה-ViewModel של התחברות
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // מחבר בין ה-ViewModel ל-XML עבור Data Binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this); // כדי שה-XML יתעדכן לפי live data

        // מאזין לאירוע ניווט להרשמה
        viewModel.getNavigateToRegister().observe(this, navigate -> {
            if (navigate) {
                // פותח את מסך ההרשמה
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // מאזין להצלחה בהתחברות
        viewModel.getLoginSuccess().observe(this, success -> {
            if (success) {
                // אם ההתחברות הצליחה – מעבר למסך הראשי וסיום הפעילות הנוכחית
                Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                startActivity(intent);
                finish(); // לא רוצה שהמשתמש יחזור אחורה למסך ההתחברות
            }
        });
    }
}
