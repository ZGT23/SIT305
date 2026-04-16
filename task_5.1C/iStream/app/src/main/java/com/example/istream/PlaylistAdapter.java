package com.example.istream;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istream.database.PlaylistVideo;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private List<PlaylistVideo> playlist;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PlaylistVideo video);
    }

    public PlaylistAdapter(List<PlaylistVideo> playlist, OnItemClickListener listener) {
        this.playlist = playlist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaylistVideo video = playlist.get(position);
        holder.tvVideoUrl.setText(video.videoUrl);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(video));
    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvVideoUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVideoUrl = itemView.findViewById(R.id.tv_video_url);
        }
    }
}
