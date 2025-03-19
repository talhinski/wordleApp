package com.hinski.wordelapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.hinski.wordelapplication.view.GameBoardFragment;
import com.hinski.wordelapplication.view.KeyboardFragment;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.game_board_container, new GameBoardFragment());
            transaction.replace(R.id.keyboard_container, new KeyboardFragment());
            transaction.commit();
        }
    }
}