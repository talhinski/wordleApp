package com.hinski.wordelapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.hinski.wordelapplication.view.GameBoardFragment;
import com.hinski.wordelapplication.view.KeyboardFragment;
import com.hinski.wordelapplication.viewmodel.GameViewModel;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        
        Button returnButton = findViewById(R.id.btn_return);
        returnButton.setOnClickListener(v -> {
            finish(); // return to the main game screen
        });
        GameViewModel gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        Button hintButton = findViewById(R.id.btn_hint);
        hintButton.setOnClickListener(v -> {
            gameViewModel.getHint();
        });

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.game_board_container, new GameBoardFragment());
            transaction.replace(R.id.keyboard_container, new KeyboardFragment());
            transaction.commit();
        }
    }
}