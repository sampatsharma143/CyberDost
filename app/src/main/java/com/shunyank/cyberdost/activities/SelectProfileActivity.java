package com.shunyank.cyberdost.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shunyank.cyberdost.MainActivity;
import com.shunyank.cyberdost.databinding.ActivitySelectProfileBinding;
import com.shunyank.cyberdost.network.callbacks.DocumentCreateListener;
import com.shunyank.cyberdost.network.utils.DatabaseUtils;
import com.shunyank.cyberdost.utils.AppUtils;

import java.util.HashMap;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Document;
import kotlin.Result;

public class SelectProfileActivity extends AppCompatActivity {
    String selectedProfile = "";
    ActivitySelectProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String userId = getIntent().getStringExtra("user_id");


        binding.maleOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeProfile("male_one.png");

            }
        });

        binding.maleTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfile("male_two.png");

            }
        });

        binding.maleThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfile("male_three.png");

            }
        });

        binding.femaleOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfile("female_one.png");

            }
        });
        binding.femaleTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfile("female_two.png");

            }
        });
        binding.femaleThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfile("female_three.png");

            }
        });
        binding.completeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // request to the server
                if(selectedProfile.isEmpty()){
                    Toast.makeText(SelectProfileActivity.this, "Please select one profile!", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<Object,Object> data = new HashMap<>();
                data.put("user_profile",selectedProfile);
                data.put("user_id",userId);
                DatabaseUtils.createDocument(SelectProfileActivity.this, "collection-user", "unique()", data, new DocumentCreateListener() {
                    @Override
                    public void onCreatedSuccessfully(Document document) {
                        AppUtils.Companion.setUserProfile(selectedProfile);
                        Toast.makeText(SelectProfileActivity.this, "Profile Completed Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SelectProfileActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailed(Result.Failure failure) {
                        Toast.makeText(SelectProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onException(AppwriteException exception) {
                        Toast.makeText(SelectProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    public void changeProfile(String name){
        String url = "https://shunyank.in/avatars/"+name;
        Glide.with(SelectProfileActivity.this).load(url).into(binding.mainProfile);
        selectedProfile = url;
    }

}