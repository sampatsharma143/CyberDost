package com.shunyank.cyberdost.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shunyank.cyberdost.databinding.ActivityCreatePostBinding;
import com.shunyank.cyberdost.models.CreatePostModel;
import com.shunyank.cyberdost.network.callbacks.DocumentCreateListener;
import com.shunyank.cyberdost.network.utils.DatabaseUtils;
import com.shunyank.cyberdost.utils.AppUtils;

import java.util.HashMap;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Account;
import io.appwrite.models.Document;
import kotlin.Result;

public class CreatePostActivity extends AppCompatActivity {
    ActivityCreatePostBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.username.setText(AppUtils.Companion.getUser().getName());
        Glide.with(this).load(AppUtils.Companion.getUserProfileString()).into(binding.userpicImageview);
        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePostActivity.super.onBackPressed();
            }
        });
        binding.nextButtonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.postContent.getText().toString().isEmpty()){
                    Toast.makeText(CreatePostActivity.this, "Please write something", Toast.LENGTH_SHORT).show();
                    return;
                }

                Account account = AppUtils.Companion.getUser();
                String email = account.getEmail();
                String id = account.getId();
                String usersName= account.getName();
                String userHandler = AppUtils.Companion.getUserHandle(email);
                String postContent = binding.postContent.getText().toString().trim();
                Log.e("userhandler",userHandler);
                Log.e("id",id);
                CreatePostModel createPostModel = new CreatePostModel(
                        postContent,
                        "Scam",
                        id,
                        "DEACTIVE",
                        usersName,
                        AppUtils.Companion.getUserProfileString(),
                        email,
                        userHandler
                );

                progressDialog = new ProgressDialog(CreatePostActivity.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

//                new CountDownTimer(1500,1000){
//
//                    @Override
//                    public void onTick(long l) {
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//
//                        if(progressDialog.isShowing()){
//                            progressDialog.dismiss();
//                        }
//                        Intent intent = new Intent(CreatePostActivity.this,SelectCatgoriesActivity.class);
//                        startActivity(intent);
//
//                    }
//                }.start();
//

                HashMap<Object,Object> dataToPost = DatabaseUtils.convertToHashmap(createPostModel);
                DatabaseUtils.createDocument(CreatePostActivity.this, "collection-post", "unique()", dataToPost, new DocumentCreateListener() {
                            @Override
                            public void onCreatedSuccessfully(Document document) {

                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }

                                Intent intent = new Intent(CreatePostActivity.this,SelectCatgoriesActivity.class);
                                intent.putExtra("doc_id",document.getId());

                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailed(Result.Failure failure) {

                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(CreatePostActivity.this, "Couldn't upload your post.Something went wrong", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onException(AppwriteException exception) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(CreatePostActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                );


//

            }
        });


    }
}