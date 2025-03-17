package com.hinski.wordelapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.hinski.wordelapplication.databinding.ComponentCellBinding;
import com.hinski.wordelapplication.model.CharResult;


public class CellComponent extends FrameLayout {

    private ComponentCellBinding binding;


    public CellComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);

    }

    private void initialize(@NonNull Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ComponentCellBinding.inflate(inflater, this, true);

    }

    public void bind(CharResult charResult) {
        binding.setCell(charResult);
    }


}