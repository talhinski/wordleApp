package com.hinski.wordelapplication.viewmodel;

import androidx.lifecycle.ViewModel;

import com.hinski.wordelapplication.model.CharResult;

public class CellViewModel extends ViewModel {
    private CharResult charResult;

    public CellViewModel(CharResult charResult) {
        this.charResult = charResult;
    }

    public CharResult getCharResult() {
        return charResult;
    }

    public void setCharResult(CharResult charResult) {
        this.charResult = charResult;
    }
}
