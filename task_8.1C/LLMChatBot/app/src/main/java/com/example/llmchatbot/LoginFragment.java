package com.example.llmchatbot;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

/**
 * LoginFragment handles the initial user login.
 * Users enter a username to proceed to the chat screen.
 */
public class LoginFragment extends Fragment {

    private TextInputEditText etUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.et_username);
        Button btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(getContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
            } else {
                // Navigate to ChatFragment via MainActivity
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToChat(username);
                }
            }
        });

        return view;
    }
}
