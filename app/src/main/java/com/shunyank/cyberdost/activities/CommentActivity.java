package com.shunyank.cyberdost.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.shunyank.cyberdost.databinding.ActivityCommentBinding;

public class CommentActivity extends AppCompatActivity {

    ActivityCommentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentActivity.super.onBackPressed();
            }
        });
    }
}