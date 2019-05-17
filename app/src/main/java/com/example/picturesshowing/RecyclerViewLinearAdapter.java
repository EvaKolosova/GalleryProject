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
    protected Context mContext;
    private ArrayList<ListStructure> f;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;

    RecyclerViewLinearAdapter(Context context, ArrayList<ListStructure> f, OnItemClickListener onItemClickListener) {

        this.mInflater = LayoutInflater.from(context);
        this.f = f;
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

        ListStructure itemData = f.get(position);
        Glide
                .with(mContext)
                .load(itemData.image1)
                .into(holder.myImageView);
        Glide
                .with(mContext)
                .load(itemData.image2)
                .into(holder.myImageView2);

        holder.myImageView.setOnClickListener((View v)-> {
            mListener.onItemClick(v, position, "myImageView");
            Log.d("POSimage1", "position 1 is " + position);
        });

        holder.myImageView2.setOnClickListener((View v) -> {
            mListener.onItemClick(v, position, "myImageView2");
            Log.d("POSimage2", "position 2 is " + position);
        });
    }

    // parent activity will implement this method to respond to click events
    public interface OnItemClickListener {
        void onItemClick(View view, int position, String name);
    }

    @Override
    public int getItemCount() {
        return f.size();
    }

    public long getItemId(int position) { return position;}

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        protected ImageView myImageView;
        protected ImageView myImageView2;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.imageFirst);
            myImageView2 = itemView.findViewById(R.id.imageSecond);
        }
    }
}
