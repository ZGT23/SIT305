package com.example.learningassistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learningassistant.R;
import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {
    private final List<String> interests;
    private final List<String> selected;

    public InterestAdapter(List<String> interests, List<String> selected) {
        this.interests = interests;
        this.selected = selected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_interest, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String interest = interests.get(position);
        holder.tvInterest.setText(interest);
        boolean isSelected = selected.contains(interest);
        holder.tvInterest.setBackgroundResource(
            isSelected ? R.drawable.bg_chip_selected : R.drawable.bg_chip_unselected
        );

        holder.itemView.setOnClickListener(v -> {
            if (selected.contains(interest)) {
                selected.remove(interest);
            } else if (selected.size() < 10) {
                selected.add(interest);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() { return interests.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInterest;
        ViewHolder(View itemView) {
            super(itemView);
            tvInterest = itemView.findViewById(R.id.tvInterest);
        }
    }
}
