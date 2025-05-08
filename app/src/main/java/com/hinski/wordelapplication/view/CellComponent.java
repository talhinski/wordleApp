package com.hinski.wordelapplication.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.hinski.wordelapplication.databinding.ComponentCellBinding;
import com.hinski.wordelapplication.model.CharResult;
import com.hinski.wordelapplication.model.LetterResult;

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
        if (charResult.result.get() != LetterResult.EMPTY)
            performFlipAnimation(charResult);
        else
            binding.setCell(charResult);
    }

    private void performFlipAnimation(CharResult charResult) {
        // Animate the flip
        ObjectAnimator flipOut = ObjectAnimator.ofFloat(this, "rotationY", 0f, 90f);
        flipOut.setDuration(250);
        flipOut.addListener(new CellAnimatorListenerAdapter(charResult));
        flipOut.start();
    }

    private class CellAnimatorListenerAdapter extends AnimatorListenerAdapter {
        private final CharResult charResult;

        public CellAnimatorListenerAdapter(CharResult charResult) {
            this.charResult = charResult;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            // Update the content after the first half of the flip
            binding.setCell(charResult);

            // Set the background color after the animation starts
            binding.letter.setBackgroundColor(charResult.backgroundColor.get());

            // Flip back to 0 degrees
            ObjectAnimator flipIn = ObjectAnimator.ofFloat(CellComponent.this, "rotationY", 90f, 0f);
            flipIn.setDuration(10);
            flipIn.start();
        }
    }
}
