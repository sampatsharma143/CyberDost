package com.shunyank.cyberdost.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shunyank.cyberdost.R;
import com.shunyank.cyberdost.databinding.ActivitySmsCheckerBinding;
import com.shunyank.cyberdost.models.SpamSMSModel;
import com.shunyank.cyberdost.network.RetrofitAPI;
import com.shunyank.cyberdost.network.RetrofitHelper;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmsCheckerActivity extends AppCompatActivity {

    ActivitySmsCheckerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySmsCheckerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsCheckerActivity.super.onBackPressed();
            }
        });
        binding.analysSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sms = binding.smsEdittext.getText().toString().trim();
                if(sms.isEmpty()){
                    Toast.makeText(SmsCheckerActivity.this, "Please enter SMS to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }
                binding.scanAnimation.setVisibility(View.VISIBLE);
                RetrofitHelper retrofitHelper = new RetrofitHelper();
               RetrofitAPI retrofitAPI =  retrofitHelper.getRetrofitApi("https://sms-spam-detector-rg0c.onrender.com");
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("sms_text", sms)
                        .build();
                Call<SpamSMSModel> modelCall = retrofitAPI.detectSpam(requestBody);
                modelCall.enqueue(new Callback<SpamSMSModel>() {
                    @Override
                    public void onResponse(Call<SpamSMSModel> call, Response<SpamSMSModel> response) {



                        SpamSMSModel spamSMSModel = response.body();
//                        Log.e("spam",""+spamSMSModel.getSpam_score());
                        binding.scanAnimation.setVisibility(View.GONE);

//                        Log.e("response","onResponse");
                        if(spamSMSModel!=null) {
                            Intent intent = new Intent(SmsCheckerActivity.this, SMSSpamCheckResultActivity.class);
                            intent.putExtra("spam_score", spamSMSModel.getSpam_score());
                            intent.putExtra("sms_content", sms);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<SpamSMSModel> call, Throwable t) {
                        binding.scanAnimation.setVisibility(View.GONE);
                        Toast.makeText(SmsCheckerActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("response","onFailure");
                        t.printStackTrace();

                    }
                });



            }
        });
    }

}
