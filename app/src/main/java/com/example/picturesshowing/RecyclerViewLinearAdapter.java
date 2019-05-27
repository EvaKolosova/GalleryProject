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

public class RecyclerViewLinearAdapter extends RecyclerView.Adapter<RecyclerViewLinearAdapter.ViewHolder> {
    private ArrayList<ListStructure> imagesPaths;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;

    RecyclerViewLinearAdapter(Context context, ArrayList<ListStructure> imagesPaths, OnItemClickListener onItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.imagesPaths = imagesPaths;
        mContext = context;
        mListener = onItemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.linear_row_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the ImageView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ListStructure itemData = imagesPaths.get(position);
        Glide
                .with(mContext)
                .load(itemData.imageOne)
                .into(holder.ivFirst);
        Glide
                .with(mContext)
                .load(itemData.imageTwo)
                .into(holder.ivSecond);

        holder.ivFirst.setOnClickListener((View v) -> {
            mListener.onItemClick(v, position, "ivFirst");
            Log.d("imageOne position", "position is: " + position);
        });

        holder.ivSecond.setOnClickListener((View v) -> {
            mListener.onItemClick(v, position, "ivSecond");
            Log.d("imageTwo position", "position is: " + position);
        });
    }

    @Override
    public int getItemCount() {
        return imagesPaths.size();
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
        protected ImageView ivFirst;
        protected ImageView ivSecond;

        ViewHolder(View itemView) {
            super(itemView);
            ivFirst = itemView.findViewById(R.id.imageFirst);
            ivSecond = itemView.findViewById(R.id.imageSecond);
        }
    }
}
