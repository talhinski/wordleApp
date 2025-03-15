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

import com.hinski.wordelapplication.R;
import com.hinski.wordelapplication.databinding.FragmentKeyboardBinding;
import com.hinski.wordelapplication.viewmodel.GameViewModel;

public class KeyboardFragment extends Fragment {

    private FragmentKeyboardBinding binding;
    private GameViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentKeyboardBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupKeyboardListeners(view);
    }

    private void setupKeyboardListeners(@NonNull View view) {
        ViewGroup rootLayout = (ViewGroup) view;
        setButtonListenersRecursively(rootLayout);
    }

    private void setButtonListenersRecursively(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setOnClickListener(v -> {
                    String buttonText = button.getText().toString();
                    if (buttonText.equals("Enter")) {
                        viewModel.testCurrentGuess();
                    } else if (buttonText.equals("Delete")) {
                        viewModel.deleteChar();
                    } else if (buttonText.length() == 1) {
                        viewModel.enterChar(buttonText.charAt(0));
                    }
                });
            } else if (child instanceof ViewGroup) {
                setButtonListenersRecursively((ViewGroup) child);
            }
        }
    }
}
