package com.shunyank.cyberdost.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shunyank.cyberdost.R;
import com.shunyank.cyberdost.databinding.ActivitySmsspamCheckResultBinding;

public class SMSSpamCheckResultActivity extends AppCompatActivity {

    ActivitySmsspamCheckResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySmsspamCheckResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        int spamScore = getIntent().getIntExtra("spam_score",-1);
        String smsContent = getIntent().getStringExtra("sms_content");

        if(spamScore==-1){
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }

        binding.smsContent.setText(smsContent);
        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSpamCheckResultActivity.super.onBackPressed();
            }
        });
        // sms is spam
        if(spamScore==1){
            binding.smsStatus.setText( "This SMS Looks Scammy");
            binding.smsStatus.setTextColor(getResources().getColor(R.color.warning_red));
            binding.smsLayout.setBackground(getResources().getDrawable(R.drawable.scam_sms_text_bg));

        }
        else {
            binding.smsStatus.setText( "This SMS is okay");
            binding.smsStatus.setTextColor(getResources().getColor(R.color.okay_green));
            binding.smsLayout.setBackground(getResources().getDrawable(R.drawable.okay_sms_text_bg));
        }


    }
}