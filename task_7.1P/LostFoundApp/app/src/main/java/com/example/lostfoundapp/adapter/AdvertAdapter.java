package com.example.lostfoundapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostfoundapp.R;
import com.example.lostfoundapp.model.Advert;

import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.AdvertViewHolder> {

    private Context context;
    private List<Advert> advertList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Advert advert);
    }

    public AdvertAdapter(Context context, List<Advert> advertList, OnItemClickListener listener) {
        this.context = context;
        this.advertList = advertList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_advert, parent, false);
        return new AdvertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder holder, int position) {
        Advert advert = advertList.get(position);
        holder.tvItemType.setText(advert.getType());
        holder.tvItemDescription.setText(advert.getDescription());
        holder.tvItemCategory.setText("Category: " + advert.getCategory());
        holder.tvItemLocation.setText("Location: " + advert.getLocation());
        holder.tvItemTimestamp.setText(advert.getTimestamp());

        if (advert.getImageUri() != null) {
            holder.ivItemThumbnail.setImageURI(Uri.parse(advert.getImageUri()));
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(advert));
    }

    @Override
    public int getItemCount() {
        return advertList.size();
    }

    public void setAdverts(List<Advert> advertList) {
        this.advertList = advertList;
        notifyDataSetChanged();
    }

    public static class AdvertViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemThumbnail;
        TextView tvItemType, tvItemDescription, tvItemCategory, tvItemLocation, tvItemTimestamp;

        public AdvertViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemThumbnail = itemView.findViewById(R.id.ivItemThumbnail);
            tvItemType = itemView.findViewById(R.id.tvItemType);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            tvItemCategory = itemView.findViewById(R.id.tvItemCategory);
            tvItemLocation = itemView.findViewById(R.id.tvItemLocation);
            tvItemTimestamp = itemView.findViewById(R.id.tvItemTimestamp);
        }
    }
}
