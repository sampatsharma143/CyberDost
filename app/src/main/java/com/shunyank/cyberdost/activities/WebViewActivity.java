package com.shunyank.cyberdost.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.shunyank.cyberdost.R;
import com.shunyank.cyberdost.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        if(title.length()>30){
            title = title.substring(0,27) +"...";
        }
        binding.title.setText(title);
        binding.progressBar.setVisibility(View.VISIBLE);
//        getWindow().statusBarColor = ContextCompat.getColor(this, R.color.white);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress>85){
                    binding.progressBar.setVisibility(View.GONE);
                }else {
                    if(binding.progressBar.getVisibility()==View.GONE){
                        binding.progressBar.setVisibility(View.VISIBLE);

                    }
                }
            }
        });
        binding.webview.loadUrl(url);

        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.super.onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(binding.webview.canGoBack()){
            binding.webview.goBack();
        }else {
            super.onBackPressed();

        }
    }
}