package com.example.eventplanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventListFragment extends Fragment {

    private RecyclerView recyclerViewEvents;
    private EventAdapter adapter;
    private List<Event> eventList;

    private EventDatabase database;
    private ExecutorService executorService;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewEvents = view.findViewById(R.id.recyclerViewEvents);

        eventList = new ArrayList<>();
        adapter = new EventAdapter(eventList, new EventAdapter.OnEventActionListener() {
            @Override
            public void onEditClick(Event event) {
                showEditDialog(event, view);
            }

            @Override
            public void onDeleteClick(Event event) {
                deleteEvent(event, view);
            }
        });

        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewEvents.setAdapter(adapter);

        database = EventDatabase.getDatabase(requireContext());
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadEvents();
    }

    private void loadEvents() {
        executorService.execute(() -> {
            List<Event> events = database.eventDao().getAllEvents();
            requireActivity().runOnUiThread(() -> adapter.setEventList(events));
        });
    }

    private void deleteEvent(Event event, View rootView) {
        executorService.execute(() -> {
            database.eventDao().delete(event);

            requireActivity().runOnUiThread(() -> {
                Snackbar.make(rootView, "Event deleted successfully", Snackbar.LENGTH_SHORT).show();
                loadEvents();
            });
        });
    }

    private void showEditDialog(Event event, View rootView) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_event, null);

        EditText editTextTitle = dialogView.findViewById(R.id.editTextEditTitle);
        Spinner spinnerCategory = dialogView.findViewById(R.id.spinnerEditCategory);
        EditText editTextLocation = dialogView.findViewById(R.id.editTextEditLocation);
        EditText editTextDate = dialogView.findViewById(R.id.editTextEditDate);

        String[] categories = {"Work", "Social", "Travel"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);

        editTextTitle.setText(event.getTitle());
        editTextLocation.setText(event.getLocation());
        editTextDate.setText(event.getDateTime());

        int categoryPosition = 0;
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(event.getCategory())) {
                categoryPosition = i;
                break;
            }
        }
        spinnerCategory.setSelection(categoryPosition);

        Calendar selectedDateTime = Calendar.getInstance();

        editTextDate.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (view, year, month, dayOfMonth) -> {
                        selectedDateTime.set(Calendar.YEAR, year);
                        selectedDateTime.set(Calendar.MONTH, month);
                        selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                requireContext(),
                                (timeView, hourOfDay, minute) -> {
                                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    selectedDateTime.set(Calendar.MINUTE, minute);

                                    String formattedDateTime = String.format(
                                            "%04d-%02d-%02d %02d:%02d",
                                            year, month + 1, dayOfMonth, hourOfDay, minute
                                    );
                                    editTextDate.setText(formattedDateTime);
                                },
                                now.get(Calendar.HOUR_OF_DAY),
                                now.get(Calendar.MINUTE),
                                true
                        );
                        timePickerDialog.show();
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        });

        new AlertDialog.Builder(requireContext())
                .setTitle("Edit Event")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String updatedTitle = editTextTitle.getText().toString().trim();
                    String updatedCategory = spinnerCategory.getSelectedItem().toString();
                    String updatedLocation = editTextLocation.getText().toString().trim();
                    String updatedDateTime = editTextDate.getText().toString().trim();

                    if (TextUtils.isEmpty(updatedTitle)) {
                        Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(updatedDateTime)) {
                        Toast.makeText(requireContext(), "Date cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    event.setTitle(updatedTitle);
                    event.setCategory(updatedCategory);
                    event.setLocation(updatedLocation);
                    event.setDateTime(updatedDateTime);

                    executorService.execute(() -> {
                        database.eventDao().update(event);

                        requireActivity().runOnUiThread(() -> {
                            Snackbar.make(rootView, "Event updated successfully", Snackbar.LENGTH_SHORT).show();
                            loadEvents();
                        });
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}