package com.hinski.wordelapplication.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hinski.wordelapplication.R;
import com.hinski.wordelapplication.model.CharResult;
import com.hinski.wordelapplication.model.LetterResult;
import com.hinski.wordelapplication.viewmodel.CellViewModel;

public class CellActivity extends AppCompatActivity {
    private CellViewModel cellViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cell);

        CharResult charResult = new CharResult('A', LetterResult.CORRECT); // Example initialization
        cellViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(CellViewModel.class);
        cellViewModel.setCharResult(charResult);
    }
}
