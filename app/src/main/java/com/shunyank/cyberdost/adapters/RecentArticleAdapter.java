package com.shunyank.cyberdost.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shunyank.cyberdost.R;
import com.shunyank.cyberdost.activities.WebViewActivity;
import com.shunyank.cyberdost.models.SliderModel;

import java.util.ArrayList;

public class RecentArticleAdapter extends RecyclerView.Adapter<RecentArticleAdapter.ViewHolder> {

    Context context;
    ArrayList<SliderModel> sliderModalArrayList;

    public RecentArticleAdapter(Context context, ArrayList<SliderModel> sliderModalArrayList) {
        this.context = context;
        this.sliderModalArrayList = sliderModalArrayList;
    }

    @NonNull
    @Override
    public RecentArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_layout,parent,false);

        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentArticleAdapter.ViewHolder holder, int position) {
        SliderModel modal = sliderModalArrayList.get(position);

        holder.titleTV.setText(modal.getTitle());
//        holder.authorTv.setText(modal.getAuthor());
        Glide.with(context).load(modal.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent blogIntent = new Intent(context, WebViewActivity.class);
                blogIntent.putExtra("title",modal.getTitle().trim());
                blogIntent.putExtra("url",modal.getPostUrl());
                context.startActivity(blogIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sliderModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTV,authorTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views.
             imageView = itemView.findViewById(R.id.image);
             titleTV = itemView.findViewById(R.id.title_textview);
             authorTv = itemView.findViewById(R.id.author_textview);

        }
    }
}
