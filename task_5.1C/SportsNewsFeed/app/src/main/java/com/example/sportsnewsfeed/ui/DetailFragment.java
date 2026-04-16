package com.example.sportsnewsfeed.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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

public class DetailFragment extends Fragment {

    private static final String ARG_NEWS_ITEM = "news_item";
    private NewsItem newsItem;
    private SharedPreferences sharedPreferences;
    private boolean isBookmarked;
    private ImageButton btnBookmark;

    public static DetailFragment newInstance(NewsItem item) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsItem = (NewsItem) getArguments().getSerializable(ARG_NEWS_ITEM);
        }
        sharedPreferences = requireActivity().getSharedPreferences("bookmarks_pref", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageView ivDetail = view.findViewById(R.id.iv_detail);
        TextView tvTitle = view.findViewById(R.id.tv_detail_title);
        TextView tvCategory = view.findViewById(R.id.tv_detail_category);
        TextView tvDescription = view.findViewById(R.id.tv_detail_description);
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBookmark = view.findViewById(R.id.btn_bookmark);
        RecyclerView rvRelated = view.findViewById(R.id.rv_related);

        if (newsItem != null) {
            ivDetail.setImageResource(newsItem.getImageResourceId());
            tvTitle.setText(newsItem.getTitle());
            tvCategory.setText(newsItem.getCategory());
            tvDescription.setText(newsItem.getDescription());

            // Check if bookmarked
            Set<String> bookmarks = sharedPreferences.getStringSet("bookmarks", new HashSet<>());
            isBookmarked = bookmarks.contains(String.valueOf(newsItem.getId()));
            updateBookmarkIcon();

            // Related stories (same category)
            List<NewsItem> allNews = DummyData.getNewsItems();
            List<NewsItem> relatedNews = new ArrayList<>();
            for (NewsItem item : allNews) {
                if (item.getCategory().equals(newsItem.getCategory()) && item.getId() != newsItem.getId()) {
                    relatedNews.add(item);
                }
            }

            NewsAdapter relatedAdapter = new NewsAdapter(relatedNews, this::openDetail);
            rvRelated.setLayoutManager(new LinearLayoutManager(getContext()));
            rvRelated.setAdapter(relatedAdapter);
        }

        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        btnBookmark.setOnClickListener(v -> toggleBookmark());

        return view;
    }

    private void toggleBookmark() {
        Set<String> bookmarks = new HashSet<>(sharedPreferences.getStringSet("bookmarks", new HashSet<>()));
        String id = String.valueOf(newsItem.getId());

        if (isBookmarked) {
            bookmarks.remove(id);
            isBookmarked = false;
            Toast.makeText(getContext(), "Removed from bookmarks", Toast.LENGTH_SHORT).show();
        } else {
            bookmarks.add(id);
            isBookmarked = true;
            Toast.makeText(getContext(), "Added to bookmarks", Toast.LENGTH_SHORT).show();
        }

        sharedPreferences.edit().putStringSet("bookmarks", bookmarks).apply();
        updateBookmarkIcon();
    }

    private void updateBookmarkIcon() {
        if (isBookmarked) {
            btnBookmark.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            btnBookmark.setImageResource(android.R.drawable.btn_star_big_off);
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
