package com.hinski.wordelapplication.logic;


import android.content.res.Resources;

import com.hinski.wordelapplication.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class WordelWords {
    private final Set<String> words = new HashSet<>();

    public WordelWords(Resources res) {
        loadWords(res);
    }

    private void loadWords(Resources res) {
         //read the words list from the file
         try {
            InputStream in_s = res.openRawResource(R.raw.words);
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(in_s));
            String line;
            while ((line = reader.readLine()) != null) {
                //split the words by the comma
                String[] curreStrings = line.split(",");
                if (curreStrings.length > 1) {
                    words.add(curreStrings[1]);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getWords() {
        return words;
    }
}
