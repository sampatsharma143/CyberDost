package com.shunyank.cyberdost.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shunyank.cyberdost.PostStatus;
import com.shunyank.cyberdost.adapters.SelectCatAdapter;
import com.shunyank.cyberdost.databinding.ActivitySelectCatgoriesBinding;
import com.shunyank.cyberdost.models.ScamCatModel;
import com.shunyank.cyberdost.models.UpdatePostModel;
import com.shunyank.cyberdost.network.callbacks.DocumentUpdateListener;
import com.shunyank.cyberdost.network.utils.DatabaseUtils;

import java.util.ArrayList;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Document;
import kotlin.Result;

public class SelectCatgoriesActivity extends AppCompatActivity {

    ArrayList<ScamCatModel> scamCatModels;
    ActivitySelectCatgoriesBinding binding;
    String selectedCats = "";
    String docId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectCatgoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        scamCatModels = new ArrayList<>();
        docId = getIntent().getStringExtra("doc_id");
        scamCatModels.add(new ScamCatModel("Phishing",false));
        scamCatModels.add(new ScamCatModel("Spoofing",false));
        scamCatModels.add(new ScamCatModel("Malware",false));
        scamCatModels.add(new ScamCatModel("Ransomware",false));
        scamCatModels.add(new ScamCatModel("Botnets",false));
        scamCatModels.add(new ScamCatModel("Social engineering",false));
        scamCatModels.add(new ScamCatModel("Identity theft",false));
        scamCatModels.add(new ScamCatModel("Hacking",false));
        scamCatModels.add(new ScamCatModel("Cyberbullying",false));
        scamCatModels.add(new ScamCatModel("Catfishing",false));
        scamCatModels.add(new ScamCatModel("Sextortion",false));
        scamCatModels.add(new ScamCatModel("Ponzi",false));
        scamCatModels.add(new ScamCatModel("Pyramid",false));
        scamCatModels.add(new ScamCatModel("Investment",false));
        scamCatModels.add(new ScamCatModel("Tech-support",false));
        scamCatModels.add(new ScamCatModel("Cryptojacking",false));


        GridLayoutManager manager = new GridLayoutManager(this,2);
        binding.scamCatRyc.setLayoutManager(manager);
        SelectCatAdapter adapter = new SelectCatAdapter(this,scamCatModels);
        binding.scamCatRyc.setAdapter(adapter);

        adapter.setSelectCatCallback(new SelectCatAdapter.SelectCatCallback() {
            @Override
            public void onDataChange() {
                selectedCats = "";
                for (ScamCatModel model:scamCatModels
                     ) {

                    if(model.isSelected()) {
                        if(selectedCats.isEmpty()){

                            selectedCats = model.getName()+",";
                        }else {
                            selectedCats = selectedCats+model.getName()+",";
                        }
                        Log.e("is selected", selectedCats    );
                        Log.e("last removed",selectedCats.substring(0,selectedCats.length()-1));
                    }
                }
            }
        });


        binding.postButtonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedCats.isEmpty()){
                    Toast.makeText(SelectCatgoriesActivity.this, "Please select at least one category", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProgressDialog progressDialog = new ProgressDialog(SelectCatgoriesActivity.this);
                progressDialog.setMessage("Creating Post...");
                progressDialog.show();
                UpdatePostModel postModel = new UpdatePostModel(PostStatus.ACTIVE.name(),selectedCats);

                DatabaseUtils.updateDocument(SelectCatgoriesActivity.this, "collection-post", docId, DatabaseUtils.convertToHashmap(postModel), new DocumentUpdateListener() {
                    @Override
                    public void onUpdatedSuccessfully(Document document) {

                      new CountDownTimer(1200,1000){

                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                        Toast.makeText(SelectCatgoriesActivity.this, "Post Created Successfully", Toast.LENGTH_SHORT).show();
                                        finish();

                                    }
                                });
                            }
                        }.start();
                    }

                    @Override
                    public void onFailed(Result.Failure failure) {

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onException(AppwriteException exception) {

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });

            }
        });

        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectCatgoriesActivity.super.onBackPressed();
            }
        });

    }
}