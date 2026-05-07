package com.example.llmchatbot;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * MainActivity serves as the entry point and host for Login and Chat fragments.
 * It manages fragment transactions to switch between the login and chat screens.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If this is the first time the activity is created, show the LoginFragment.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }

    /**
     * Navigates to the ChatFragment with the provided username.
     * This method is called from LoginFragment when the user logs in.
     */
    public void navigateToChat(String username) {
        Fragment chatFragment = ChatFragment.newInstance(username);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, chatFragment)
                .addToBackStack(null) // Adds to back stack so user can navigate back to login
                .commit();
    }
}
