package com.hinski.wordelapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hinski.wordelapplication.databinding.FragmentGameBoardBinding;
import com.hinski.wordelapplication.model.Guess;
import com.hinski.wordelapplication.viewmodel.GameViewModel;

public class GameBoardFragment extends Fragment {

    private FragmentGameBoardBinding binding;
    private GameViewModel gameViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGameBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        gameViewModel.attempts.observe(getViewLifecycleOwner(), attempts -> {
            binding.gameBoard.removeAllViews(); // Clear existing rows
            for (Guess guess : attempts) {
                GameRowComponent gameRowComponent = new GameRowComponent(getContext(), null);
                gameRowComponent.bind(guess);
                binding.gameBoard.addView(gameRowComponent);
            }
        });
        gameViewModel.invalidWordEvent
                .observe(getViewLifecycleOwner(),
                        message -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT)
                                .show()
                );
    }
}
