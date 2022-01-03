package com.example.madingapp.Retrofit;

import com.example.madingapp.Helper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + Helper.TOKEN)
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    public static ApiEndPoint endPoint() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiEndPoint.class);
    }

}
