package com.hinski.wordelapplication;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;


import com.hinski.wordelapplication.databinding.ActivityMainBinding;
import com.hinski.wordelapplication.logic.WordleLogic;
import com.hinski.wordelapplication.model.Guess;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WordleLogic wordleLogic;  // declare WordleLogic instance
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Initialize data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Initialize WordleLogic with resources (using auto-selected secret word)
        wordleLogic = new WordleLogic(getResources());
        binding.setLogic(wordleLogic); // bind (for future use as needed)

        // Set up submit button listener to process guesses.
        binding.submitButton.setOnClickListener(v -> {
            String guess = binding.guessInput.getText().toString().trim();
            if (!guess.isEmpty() && wordleLogic.isValidWord(guess)) {
                Guess currentGuess = wordleLogic.processGuess(guess);
                // For demonstration, update resultDisplay with a summary of attempts.


                binding.resultDisplay.setText(currentGuess.getWord() +
                        " " +
                        currentGuess.getResults());
                binding.guessInput.setText("");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}