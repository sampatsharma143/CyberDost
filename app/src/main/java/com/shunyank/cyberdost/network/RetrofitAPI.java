package com.shunyank.cyberdost.network;


import com.shunyank.cyberdost.models.SpamSMSModel;
import com.shunyank.cyberdost.models.UrlSpamScoreModel;
import com.shunyank.cyberdost.models.VirusTotalFirstResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

//    @POST("generate_otp")
//    Call<OtpModel> GenerateOtp(@Body HashMap<String, String> phone);

    @POST("api/detect")
    Call<SpamSMSModel> detectSpam(@Body RequestBody body);


    @Headers({
            "accept: application/json",
            "x-apikey: 7f54d3173fc2506517d3dc098dfa74714179f6e449d1afcb96553b16b852d293",
    })
    @POST("api/v3/urls")
    Call<VirusTotalFirstResponse> detectUrlSpam(@Body RequestBody body);

    @Headers({
            "accept: application/json",
            "x-apikey: 7f54d3173fc2506517d3dc098dfa74714179f6e449d1afcb96553b16b852d293",
    })
    @GET("api/v3/analyses/{id}")
    Call<UrlSpamScoreModel> getUrlSpamScore(@Path("id") String id);
}
