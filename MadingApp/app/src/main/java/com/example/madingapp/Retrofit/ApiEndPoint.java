package com.example.madingapp.Retrofit;

import com.example.madingapp.Helper;
import com.example.madingapp.Models.Request.AuthRequest;
import com.example.madingapp.Models.Response.AuthResponse;
import com.example.madingapp.Models.Response.BookMarkResponse;
import com.example.madingapp.Models.Response.MadingResponse;
import com.example.madingapp.Models.Response.MeResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiEndPoint {

    String apiVersion = "api/v" + Helper.API_VERSION + "/";

    // User API
    @GET(apiVersion + "users/me")
    Call<MeResponse> userGetMeGet();

    @GET(apiVersion + "users/image")
    Call<ResponseBody> userImageGet();

    // BookMark API
    @GET(apiVersion + "book-marks")
    Call<BookMarkResponse> bookMarkGet();

    // Category API

    // Auth API
    @POST(apiVersion + "auth/login")
    Call<AuthResponse> authLoginPost(@Body AuthRequest request);

    @GET(apiVersion + "auth/logout")
    Call<ResponseBody> authLogoutGet();

    // Mading API
    @GET(apiVersion + "madings")
    Call<MadingResponse> madingGet();

    @GET(apiVersion + "madings/hot-topics")
    Call<MadingResponse> madingHotTopicsGet();

    @GET(apiVersion + "madings/my-madings")
    Call<MadingResponse> madingMyMadingsGet();
}
