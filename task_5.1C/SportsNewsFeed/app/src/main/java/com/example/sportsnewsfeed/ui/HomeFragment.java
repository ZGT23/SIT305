package com.example.sportsnewsfeed.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sportsnewsfeed.R;
import com.example.sportsnewsfeed.adapter.FeaturedAdapter;
import com.example.sportsnewsfeed.adapter.NewsAdapter;
import com.example.sportsnewsfeed.data.DummyData;
import com.example.sportsnewsfeed.model.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvFeatured, rvNews;
    private FeaturedAdapter featuredAdapter;
    private NewsAdapter newsAdapter;
    private List<NewsItem> allNews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvFeatured = view.findViewById(R.id.rv_featured);
        rvNews = view.findViewById(R.id.rv_news);
        SearchView searchView = view.findViewById(R.id.search_view);
        View btnBookmarks = view.findViewById(R.id.btn_bookmarks);

        allNews = DummyData.getNewsItems();

        List<NewsItem> featuredNews = new ArrayList<>();
        List<NewsItem> latestNews = new ArrayList<>();
        for (NewsItem item : allNews) {
            if (item.isFeatured()) featuredNews.add(item);
            else latestNews.add(item);
        }

        featuredAdapter = new FeaturedAdapter(featuredNews, this::openDetail);
        rvFeatured.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFeatured.setAdapter(featuredAdapter);

        newsAdapter = new NewsAdapter(latestNews, this::openDetail);
        rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNews.setAdapter(newsAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                newsAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newsAdapter.filter(newText);
                return false;
            }
        });

        btnBookmarks.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new BookmarksFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void openDetail(NewsItem item) {
        DetailFragment detailFragment = DetailFragment.newInstance(item);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
