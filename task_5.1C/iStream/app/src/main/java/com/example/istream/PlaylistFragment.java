package com.example.istream;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istream.database.AppDatabase;
import com.example.istream.database.PlaylistVideo;

import java.util.List;

public class PlaylistFragment extends Fragment {

    private RecyclerView rvPlaylist;
    private Button btnBackHome;
    private PlaylistAdapter adapter;
    private SessionManager sessionManager;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        rvPlaylist = view.findViewById(R.id.rv_playlist);
        btnBackHome = view.findViewById(R.id.btn_back_home);

        sessionManager = new SessionManager(requireContext());
        db = AppDatabase.getInstance(requireContext());

        rvPlaylist.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        loadPlaylist();

        btnBackHome.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).navigateToHome();
        });

        return view;
    }

    private void loadPlaylist() {
        List<PlaylistVideo> playlist = db.playlistDao().getUserPlaylist(sessionManager.getUserId());
        adapter = new PlaylistAdapter(playlist, video -> {
            // When an item is clicked, go back to Home and load this video
            Fragment homeFragment = HomeFragment.newInstance(video.videoUrl);
            ((MainActivity) requireActivity()).replaceFragment(homeFragment);
        });
        rvPlaylist.setAdapter(adapter);
    }
}
