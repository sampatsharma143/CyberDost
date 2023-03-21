package com.shunyank.cyberdost.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.shunyank.cyberdost.R;
import com.shunyank.cyberdost.activities.WebViewActivity;
import com.shunyank.cyberdost.models.SliderModel;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {
    Context context;
    ArrayList<SliderModel> sliderModalArrayList;

    public SliderAdapter(Context context,ArrayList<SliderModel> sliderModalArrayList){
        this.context = context;
        this.sliderModalArrayList = sliderModalArrayList;
    }

    @Override
    public int getCount() {
        return sliderModalArrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.home_slider_layout, container, false);

        // initializing our views.
        ImageView imageView = view.findViewById(R.id.image);
        TextView titleTV = view.findViewById(R.id.title_textview);
        TextView authorTV = view.findViewById(R.id.author_textview);

        // setting data to our views.
        SliderModel modal = sliderModalArrayList.get(position);
        titleTV.setText(modal.getTitle());
        authorTV.setText(modal.getAuthor());
        Glide.with(context).load(modal.getImageUrl()).into(imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent blogIntent = new Intent(context, WebViewActivity.class);
                blogIntent.putExtra("title",modal.getTitle().trim());
                blogIntent.putExtra("url",modal.getPostUrl());
                context.startActivity(blogIntent);
            }
        });
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // this is a destroy view method
        // which is use to remove a view.
        container.removeView((CardView) object);
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (CardView) object;
    }
}
