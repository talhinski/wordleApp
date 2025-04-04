package com.hinski.wordelapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.databinding.Observable;
import androidx.databinding.Observable.OnPropertyChangedCallback;

import com.hinski.wordelapplication.databinding.ComponentGameRowBinding;
import com.hinski.wordelapplication.model.CharResult;
import com.hinski.wordelapplication.model.Guess;

import java.util.ArrayList;
import java.util.List;

public class GameRowComponent extends LinearLayout {

    private ComponentGameRowBinding binding;
    private final List<CellComponent> cells = new ArrayList<>();

    public GameRowComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ComponentGameRowBinding.inflate(inflater, this, true);
    }

    public void bind(Guess guess) {
        // On first run, create cell components and add them immediately.
        if (cells.isEmpty()) {
            for (int i = 0; i < guess.getCharResults().size(); i++) {
                CellComponent cellComponent = new CellComponent(getContext(), null);
                cells.add(cellComponent);
                binding.gameRowRoot.addView(cellComponent);
            }
        }

        for (int i = 0; i < guess.getCharResults().size(); i++) {
            final int delay = i * 250;
            final CharResult newCharResult = guess.getCharResults().get(i);
            CellComponent cellComponent = cells.get(i);
            newCharResult.result.addOnPropertyChangedCallback(
                    new CellOnPropertyChangedCallback(cellComponent, newCharResult, delay)
            );
            cellComponent.bind(newCharResult);
        }
    }

    private class CellOnPropertyChangedCallback extends OnPropertyChangedCallback {
        private final CellComponent cellComponent;
        private final CharResult newCharResult;
        private final int delay;

        public CellOnPropertyChangedCallback(CellComponent cellComponent, CharResult newCharResult, int delay) {
            this.cellComponent = cellComponent;
            this.newCharResult = newCharResult;
            this.delay = delay;
        }

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            // Trigger the animation with a delay
            postDelayed(() -> cellComponent.bind(newCharResult), delay);
        }
    }
}
