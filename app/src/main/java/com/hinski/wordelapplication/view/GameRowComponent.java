package com.hinski.wordelapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.hinski.wordelapplication.databinding.ComponentGameRowBinding;
import com.hinski.wordelapplication.model.Guess;

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

    public void bind(Guess guess) {

        binding.gameRowRoot.removeAllViews(); // Clear any existing views

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < guess.getCharResults().size(); i++) {
            CellComponent cellComponent = new CellComponent(getContext(), null);
            cellComponent.bind(guess.getCharResults().get(i));
            binding.gameRowRoot.addView(cellComponent);
        }
    }
}
