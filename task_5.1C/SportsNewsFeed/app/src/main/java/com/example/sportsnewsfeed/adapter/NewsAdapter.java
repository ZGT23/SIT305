package com.example.sportsnewsfeed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sportsnewsfeed.R;
import com.example.sportsnewsfeed.model.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsItem> newsList;
    private List<NewsItem> newsListFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(NewsItem item);
    }

    public NewsAdapter(List<NewsItem> newsList, OnItemClickListener listener) {
        this.newsList = newsList;
        this.newsListFull = new ArrayList<>(newsList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsItem item = newsList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvCategory.setText(item.getCategory());
        holder.tvDescription.setText(item.getDescription());
        holder.ivNews.setImageResource(item.getImageResourceId());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void filter(String text) {
        newsList.clear();
        if (text.isEmpty()) {
            newsList.addAll(newsListFull);
        } else {
            text = text.toLowerCase();
            for (NewsItem item : newsListFull) {
                if (item.getCategory().toLowerCase().contains(text) || item.getTitle().toLowerCase().contains(text)) {
                    newsList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNews;
        TextView tvTitle, tvCategory, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNews = itemView.findViewById(R.id.iv_news);
            tvTitle = itemView.findViewById(R.id.tv_news_title);
            tvCategory = itemView.findViewById(R.id.tv_news_category);
            tvDescription = itemView.findViewById(R.id.tv_news_description);
        }
    }
}
