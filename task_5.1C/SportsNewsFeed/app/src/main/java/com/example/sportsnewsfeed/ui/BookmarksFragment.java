package com.example.sportsnewsfeed.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sportsnewsfeed.R;
import com.example.sportsnewsfeed.adapter.NewsAdapter;
import com.example.sportsnewsfeed.data.DummyData;
import com.example.sportsnewsfeed.model.NewsItem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookmarksFragment extends Fragment {

    private RecyclerView rvBookmarks;
    private TextView tvEmpty;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        rvBookmarks = view.findViewById(R.id.rv_bookmarks);
        tvEmpty = view.findViewById(R.id.tv_empty);
        ImageButton btnBack = view.findViewById(R.id.btn_back);

        sharedPreferences = requireActivity().getSharedPreferences("bookmarks_pref", Context.MODE_PRIVATE);

        loadBookmarks();

        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }

    private void loadBookmarks() {
        Set<String> bookmarkIds = sharedPreferences.getStringSet("bookmarks", new HashSet<>());
        List<NewsItem> allNews = DummyData.getNewsItems();
        List<NewsItem> bookmarkedNews = new ArrayList<>();

        for (NewsItem item : allNews) {
            if (bookmarkIds.contains(String.valueOf(item.getId()))) {
                bookmarkedNews.add(item);
            }
        }

        if (bookmarkedNews.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvBookmarks.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvBookmarks.setVisibility(View.VISIBLE);
            NewsAdapter adapter = new NewsAdapter(bookmarkedNews, this::openDetail);
            rvBookmarks.setLayoutManager(new LinearLayoutManager(getContext()));
            rvBookmarks.setAdapter(adapter);
        }
    }

    private void openDetail(NewsItem item) {
        DetailFragment detailFragment = DetailFragment.newInstance(item);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
