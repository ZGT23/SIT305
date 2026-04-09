package com.example.eventplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddEventFragment extends Fragment {

    private EditText editTextTitle, editTextLocation, editTextDate;
    private Spinner spinnerCategory;
    private Button buttonSave;

    private Calendar selectedDateTime;
    private EventDatabase database;
    private ExecutorService executorService;

    public AddEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextLocation = view.findViewById(R.id.editTextLocation);
        editTextDate = view.findViewById(R.id.editTextDate);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        buttonSave = view.findViewById(R.id.buttonSave);

        database = EventDatabase.getDatabase(requireContext());
        executorService = Executors.newSingleThreadExecutor();
        selectedDateTime = Calendar.getInstance();

        setupCategorySpinner();
        setupDateTimePicker();

        buttonSave.setOnClickListener(v -> saveEvent());
    }

    private void setupCategorySpinner() {
        String[] categories = {"Work", "Social", "Travel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void setupDateTimePicker() {
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
    }

    private void saveEvent() {
        String title = editTextTitle.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String location = editTextLocation.getText().toString().trim();
        String dateTime = editTextDate.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(dateTime)) {
            Toast.makeText(requireContext(), "Date cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar now = Calendar.getInstance();
        if (selectedDateTime.before(now)) {
            Toast.makeText(requireContext(), "Please choose a future date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        Event event = new Event(title, category, location, dateTime);

        executorService.execute(() -> {
            database.eventDao().insert(event);

            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();

                editTextTitle.setText("");
                editTextLocation.setText("");
                editTextDate.setText("");
                spinnerCategory.setSelection(0);
                selectedDateTime = Calendar.getInstance();
            });
        });
    }
}