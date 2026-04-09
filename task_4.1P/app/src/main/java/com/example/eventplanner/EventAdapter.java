package com.example.eventplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public interface OnEventActionListener {
        void onEditClick(Event event);
        void onDeleteClick(Event event);
    }

    private List<Event> eventList;
    private final OnEventActionListener listener;

    public EventAdapter(List<Event> eventList, OnEventActionListener listener) {
        this.eventList = eventList;
        this.listener = listener;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.textTitle.setText(event.getTitle());
        holder.textCategory.setText("Category: " + event.getCategory());
        holder.textLocation.setText("Location: " + event.getLocation());
        holder.textDateTime.setText("Date: " + event.getDateTime());

        holder.buttonEdit.setOnClickListener(v -> listener.onEditClick(event));
        holder.buttonDelete.setOnClickListener(v -> listener.onDeleteClick(event));
    }

    @Override
    public int getItemCount() {
        return eventList == null ? 0 : eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textCategory, textLocation, textDateTime;
        Button buttonEdit, buttonDelete;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textCategory = itemView.findViewById(R.id.textCategory);
            textLocation = itemView.findViewById(R.id.textLocation);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}