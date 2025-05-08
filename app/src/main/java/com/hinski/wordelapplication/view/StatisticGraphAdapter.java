package com.hinski.wordelapplication.view;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hinski.wordelapplication.R;
import com.hinski.wordelapplication.model.Statistics;

import java.util.List;

public class StatisticGraphAdapter extends RecyclerView.Adapter<StatisticGraphAdapter.StatisticGraphViewHolder> {

    private final List<Statistics> statistics;

    public StatisticGraphAdapter(List<Statistics> statistics) {
        this.statistics = statistics;
    }

    @NonNull
    @Override
    public StatisticGraphViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistics_item, parent, false);
        return new StatisticGraphViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticGraphViewHolder holder, int position) {
        Statistics stat = statistics.get(position);
        holder.tvAttempts.setText(String.valueOf(stat.getNumberOfAttempts()));
        holder.progressBar.setProgress(stat.getPercentage());
        holder.tvPercentage.setText(String.format("%d%%", stat.getPercentage()));
        if (position == 6) {//6 is error
            holder.progressBar.setProgressDrawable(holder.itemView.getContext().getDrawable(R.drawable.progress_bar_red));
            holder.tvAttempts.setText("X");
            holder.tvAttempts.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }

    static class StatisticGraphViewHolder extends RecyclerView.ViewHolder {
        TextView tvAttempts, tvPercentage;
        ProgressBar progressBar;

        public StatisticGraphViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttempts = itemView.findViewById(R.id.tv_attempts);
            progressBar = itemView.findViewById(R.id.progress_bar);
            tvPercentage = itemView.findViewById(R.id.tv_percentage);
        }
    }
}