package com.example.istream;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.istream.database.AppDatabase;
import com.example.istream.database.User;

public class SignUpFragment extends Fragment {

    private EditText etFullName, etUsername, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private TextView tvLoginLink;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        etFullName = view.findViewById(R.id.et_full_name);
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        btnSignUp = view.findViewById(R.id.btn_signup);
        tvLoginLink = view.findViewById(R.id.tv_login_link);

        db = AppDatabase.getInstance(requireContext());

        btnSignUp.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.userDao().getUserByUsername(username) != null) {
                Toast.makeText(requireContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(fullName, username, password);
            db.userDao().insertUser(newUser);
            Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
            ((MainActivity) requireActivity()).replaceFragment(new LoginFragment());
        });

        tvLoginLink.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).replaceFragment(new LoginFragment());
        });

        return view;
    }
}
