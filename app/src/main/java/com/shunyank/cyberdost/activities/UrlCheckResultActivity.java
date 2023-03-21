package com.shunyank.cyberdost.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shunyank.cyberdost.R;
import com.shunyank.cyberdost.databinding.ActivitySmsspamCheckResultBinding;
import com.shunyank.cyberdost.databinding.ActivityUrlCheckResultBinding;
import com.shunyank.cyberdost.models.UrlStats;

public class UrlCheckResultActivity extends AppCompatActivity {

    ActivityUrlCheckResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUrlCheckResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String data = getIntent().getStringExtra("data");
        String url = getIntent().getStringExtra("url");
        UrlStats stats = new Gson().fromJson(data,UrlStats.class);
        Log.e("stats",data);
        int melicious = stats.getMalicious();
        int harmless = stats.getHarmless();
        int suspicious = stats.getSuspicious();
        int percentage  = 0;
        if(harmless==0){
            percentage = 100;
        }else {
             percentage = ((suspicious + melicious) / harmless) * 100;
        }
//        int spamScore = getIntent().getIntExtra("spam_score",-1);
//        String smsContent = getIntent().getStringExtra("sms_content");
//
//        if(spamScore==-1){
//            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
        binding.urlContent.setText(url);
        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlCheckResultActivity.super.onBackPressed();
            }
        });
//        // sms is spam
        if(melicious>=2){
            binding.urlStatus.setText( melicious+" Antivirus software found this Malicious");
            binding.urlStatus.setTextColor(getResources().getColor(R.color.warning_red));
            binding.urlLayout.setBackground(getResources().getDrawable(R.drawable.scam_sms_text_bg));

        }
        else {
            binding.urlStatus.setText( "This Url Looks okay");
            binding.urlStatus.setTextColor(getResources().getColor(R.color.okay_green));
            binding.urlLayout.setBackground(getResources().getDrawable(R.drawable.okay_sms_text_bg));
        }


    }
}