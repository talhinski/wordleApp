// StatisticActivity.java
package com.hinski.wordelapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hinski.wordelapplication.databinding.ActivityRegisterBinding;
import com.hinski.wordelapplication.databinding.ActivityStatisticBinding;
import com.hinski.wordelapplication.view.StatisticGraphAdapter;
import com.hinski.wordelapplication.viewmodel.StatisticsViewModal;

public class StatisticActivity extends AppCompatActivity {

    private StatisticsViewModal viewModel;
    private ActivityStatisticBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatisticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(StatisticsViewModal.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getStatistics().observe(this, statistics -> {
            StatisticGraphAdapter adapter = new StatisticGraphAdapter(statistics);
            recyclerView.setAdapter(adapter);
        });
    }
}