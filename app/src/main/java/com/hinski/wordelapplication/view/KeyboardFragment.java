package com.hinski.wordelapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hinski.wordelapplication.databinding.FragmentKeyboardBinding;
import com.hinski.wordelapplication.viewmodel.GameViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KeyboardFragment extends Fragment {

    private FragmentKeyboardBinding binding;
    private GameViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentKeyboardBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        observeUsedLetters();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupKeyboardListeners(view);
    }

    private void setupKeyboardListeners(@NonNull View view) {
        for (Button button : getAllButtons((ViewGroup) view)) {
            button.setOnClickListener(v -> {
                String buttonTag = button.getTag() != null ? button.getTag().toString() : "";
                String buttonText = button.getText().toString();
                if (buttonTag.equals("Enter")) {
                    viewModel.submitCurrentGuess();
                } else if (buttonTag.equals("Delete")) {
                    viewModel.deleteChar();
                } else if (buttonText.length() == 1) {
                    viewModel.enterChar(buttonText.charAt(0));
                }
            });
        }
    }

    private void observeUsedLetters() {
        viewModel.getUsedLetters().observe(getViewLifecycleOwner(), this::updateButtonBackgrounds);
    }

    private void updateButtonBackgrounds(Map<Character, Integer> usedLetters) {
        for (Button button : getAllButtons((ViewGroup) getView())) {
            String buttonText = button.getText().toString();
            if (buttonText.length() == 1) {
                char letter = buttonText.charAt(0);
                if (usedLetters.containsKey(letter)) {
                    Integer color = usedLetters.get(letter);
                    button.setBackgroundColor(color);
                }
            }
        }
    }

    private Iterable<Button> getAllButtons(ViewGroup parent) {
        List<Button> buttons = new ArrayList<>();
        getAllButtonsRecursively(parent, buttons);
        return buttons;
    }

    private void getAllButtonsRecursively(ViewGroup parent, List<Button> buttons) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof Button) {
                buttons.add((Button) child);
            } else if (child instanceof ViewGroup) {
                getAllButtonsRecursively((ViewGroup) child, buttons);
            }
        }
    }
}
