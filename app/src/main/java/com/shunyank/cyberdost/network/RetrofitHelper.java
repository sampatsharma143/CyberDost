package com.shunyank.cyberdost.network;
//
//import static com.shunyank.cyberdost.network.Constants.NodeApiEndPoint;
//import static com.shunyank.cyberdost.network.Constants.NodeApiVersion;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
//    public static   Retrofit retrofit=null;
//    public static RetrofitAPI retrofitAPI=null;
//
    public RetrofitAPI getRetrofitApi(String baseUrl )
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        return    retrofit.create(RetrofitAPI.class);


    }
//
//
//
//    public Retrofit Retrofit() {
//
//        return retrofit;
//    }
}
