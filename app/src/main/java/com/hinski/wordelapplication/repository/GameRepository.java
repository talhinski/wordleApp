package com.hinski.wordelapplication.repository;

import android.content.res.Resources;
import com.hinski.wordelapplication.R;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameRepository {

    private final Set<String> words = new HashSet<>();

    public GameRepository(Resources res) {
        loadWords(res);
    }

    private void loadWords(Resources res) {
        try {
            InputStream in_s = res.openRawResource(R.raw.words);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in_s));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] curreStrings = line.split(",");
                if (curreStrings.length > 1) {
                    words.add(curreStrings[1]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getRandomWord() {
        List<String> vocabList = new ArrayList<>(words);
        return vocabList.get(new Random().nextInt(vocabList.size()));
    }

    public boolean isValidWord(String word) {
        return words.contains(word);
    }
}
