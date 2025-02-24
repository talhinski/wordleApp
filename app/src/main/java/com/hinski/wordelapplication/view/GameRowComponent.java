package com.hinski.wordelapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.hinski.wordelapplication.databinding.ComponentGameRowBinding;
import com.hinski.wordelapplication.model.LetterResult;

import java.util.List;

public class GameRowComponent extends LinearLayout {

    private ComponentGameRowBinding binding;

    public GameRowComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ComponentGameRowBinding.inflate(inflater, this, true);
    }

    public void bind(String word, List<LetterResult> results) {
        // Bind word and results to the UI
    }
}
