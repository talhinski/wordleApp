package com.hinski.wordelapplication;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //read the words list from the file
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.words);
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(in_s));
            String line;
            List<String> wordsList = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                //split the words by the comma
                String[] words = line.split(",");
                if (words.length > 1) {
                    wordsList.add(words[1]);
                }

            }
            WordelWords wordelWords = new WordelWords(wordsList);
            Log.i(TAG, "onCreate: " + wordelWords.words);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}