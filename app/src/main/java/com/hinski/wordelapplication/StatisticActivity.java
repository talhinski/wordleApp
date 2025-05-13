package com.hinski.wordelapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hinski.wordelapplication.databinding.ActivityStatisticBinding;
import com.hinski.wordelapplication.view.StatisticGraphAdapter;
import com.hinski.wordelapplication.viewmodel.StatisticsViewModal;

public class StatisticActivity extends AppCompatActivity {

    private StatisticsViewModal viewModel;  // ViewModel שמחזיק את הסטטיסטיקות
    private ActivityStatisticBinding binding;  // קישור בין XML למסך דרך DataBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // יוצרת את binding לקובץ activity_statistic.xml
        binding = ActivityStatisticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // מאתרת את ה-RecyclerView מה-XML ומגדירה לו תצוגת רשימה אנכית
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // יוצר את ה-ViewModel בעזרת ViewModelProvider (ממשק חכם לחיבור בין View ל-Logic)
        viewModel = new ViewModelProvider(this).get(StatisticsViewModal.class);

        // מחבר את ה-ViewModel ל-XML כך שניתן להשתמש ב-@{viewModel} בקובץ
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this); // כך ש-observe על LiveData יעבוד אוטומטית

        // מאזין לשינויים ברשימת הסטטיסטיקות – כאשר היא מוכנה, יוצר Adapter ומחבר לרשימה
        viewModel.getStatistics().observe(this, statistics -> {
            StatisticGraphAdapter adapter = new StatisticGraphAdapter(statistics);
            recyclerView.setAdapter(adapter);
        });
    }
}
