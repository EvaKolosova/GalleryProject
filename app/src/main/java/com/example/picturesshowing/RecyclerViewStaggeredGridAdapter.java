package com.example.picturesshowing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewStaggeredGridAdapter extends RecyclerView.Adapter<RecyclerViewStaggeredGridAdapter.ViewHolder> {
    private ArrayList<String> images;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;

    RecyclerViewStaggeredGridAdapter(Context context, ArrayList<String> images, OnItemClickListener onItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.images = images;
        mContext = context;
        mListener = onItemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the ImageView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String itemData = images.get(position);
        Glide
                .with(mContext)
                .load(itemData)
                .into(holder.imageView);

        holder.imageView.setOnClickListener((View v) -> {
            mListener.onItemClick(v, position, "imageView");
            Log.d("kolosova_checkInfo", "position is: " + position);
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public long getItemId(int position) {
        return position;
    }

    // parent activity will implement this method to respond to click events
    public interface OnItemClickListener {
        void onItemClick(View view, int position, String name);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.staggeredImageView);
        }
    }
}
