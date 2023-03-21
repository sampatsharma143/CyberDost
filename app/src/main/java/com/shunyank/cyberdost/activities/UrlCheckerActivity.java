package com.shunyank.cyberdost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shunyank.cyberdost.databinding.ActivitySmsCheckerBinding;
import com.shunyank.cyberdost.databinding.ActivityUrlCheckerBinding;
import com.shunyank.cyberdost.models.SpamSMSModel;
import com.shunyank.cyberdost.models.UrlSpamScoreModel;
import com.shunyank.cyberdost.models.UrlStats;
import com.shunyank.cyberdost.models.VirusTotalFirstResponse;
import com.shunyank.cyberdost.network.RetrofitAPI;
import com.shunyank.cyberdost.network.RetrofitHelper;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UrlCheckerActivity extends AppCompatActivity {

    ActivityUrlCheckerBinding binding;
    private String url ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUrlCheckerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlCheckerActivity.super.onBackPressed();
            }
        });
        binding.analysUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 url = binding.urlEdittext.getText().toString().trim();
                if(url.isEmpty()){
                    Toast.makeText(UrlCheckerActivity.this, "Please enter url to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }
                binding.scanAnimation.setVisibility(View.VISIBLE);
                RetrofitHelper retrofitHelper = new RetrofitHelper();
               RetrofitAPI retrofitAPI =  retrofitHelper.getRetrofitApi("https://www.virustotal.com");
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("url", url)
                        .build();
                Call<VirusTotalFirstResponse> modelCall = retrofitAPI.detectUrlSpam(requestBody);

                modelCall.enqueue(new Callback<VirusTotalFirstResponse>() {
                    @Override
                    public void onResponse(Call<VirusTotalFirstResponse> call, Response<VirusTotalFirstResponse> response) {
//                        Log.e("response",new Gson().toJson(response.body()));
                        VirusTotalFirstResponse data = response.body();
                        if(data!=null) {
                            fetchScore(data);
                        }else {
                            Toast.makeText(UrlCheckerActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<VirusTotalFirstResponse> call, Throwable t) {
                        Toast.makeText(UrlCheckerActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                    }
                });
//                modelCall.enqueue(new Callback<SpamSMSModel>() {
//                    @Override
//                    public void onResponse(Call<SpamSMSModel> call, Response<SpamSMSModel> response) {
//
//
////
////                        SpamSMSModel spamSMSModel = response.body();
//////                        Log.e("spam",""+spamSMSModel.getSpam_score());
////                        binding.scanAnimation.setVisibility(View.GONE);
////
//////                        Log.e("response","onResponse");
////                        if(spamSMSModel!=null) {
////                            Intent intent = new Intent(UrlCheckerActivity.this, SMSSpamCheckResultActivity.class);
////                            intent.putExtra("spam_score", spamSMSModel.getSpam_score());
////                            intent.putExtra("sms_content", sms);
////                            startActivity(intent);
////                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<SpamSMSModel> call, Throwable t) {
//                        binding.scanAnimation.setVisibility(View.GONE);
//                        Toast.makeText(UrlCheckerActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("response","onFailure");
//                        t.printStackTrace();
//
//                    }
//                });



            }
        });
    }


    public void fetchScore(VirusTotalFirstResponse virusTotalFirstResponse){
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        RetrofitAPI retrofitAPI =  retrofitHelper.getRetrofitApi("https://www.virustotal.com");
        Call<UrlSpamScoreModel> modelCall = retrofitAPI.getUrlSpamScore(virusTotalFirstResponse.getData().getId());

        modelCall.enqueue(new Callback<UrlSpamScoreModel>() {
            @Override
            public void onResponse(Call<UrlSpamScoreModel> call, Response<UrlSpamScoreModel> response) {
                UrlSpamScoreModel urlSpamScoreModel = response.body();
                Log.e("response",""+urlSpamScoreModel.getData().getAttributes().getStats().getMalicious());
                binding.scanAnimation.setVisibility(View.GONE);
                Intent intent = new Intent(UrlCheckerActivity.this,UrlCheckResultActivity.class);
                intent.putExtra("data",new Gson().toJson(urlSpamScoreModel.getData().getAttributes().getStats()).toString());
                intent.putExtra("url",url);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<UrlSpamScoreModel> call, Throwable t) {
                Toast.makeText(UrlCheckerActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

}
