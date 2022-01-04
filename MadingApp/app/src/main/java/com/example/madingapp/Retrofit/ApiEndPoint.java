package com.example.madingapp.Retrofit;

import com.example.madingapp.Helper;
import com.example.madingapp.Models.Request.AuthRequest;
import com.example.madingapp.Models.Request.MadingRequest;
import com.example.madingapp.Models.Request.SignUpRequest;
import com.example.madingapp.Models.Response.AuthResponse;
import com.example.madingapp.Models.Response.BookMarkResponse;
import com.example.madingapp.Models.Response.MadingResponse;
import com.example.madingapp.Models.Response.MeResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiEndPoint {

    String apiVersion = "api/v" + Helper.API_VERSION + "/";

    // User API
    @GET(apiVersion + "users/me")
    Call<MeResponse> userGetMeGet();

    @GET(apiVersion + "users/image")
    Call<ResponseBody> userImageGet();
    @Multipart
    @PUT(apiVersion + "users")
    Call<ResponseBody> userPut(@Part("UserId") RequestBody title,
                                  @Part("Username") RequestBody username,
                                  @Part("Firstname") RequestBody firstName,
                                  @Part("Lastname") RequestBody lastName,
                                  @Part("UserImage\"; filename=\"image.png") RequestBody fileImage);

    // BookMark API
    @GET(apiVersion + "book-marks")
    Call<BookMarkResponse> bookMarkGet();

    @GET(apiVersion + "book-marks/my-bookmarks")
    Call<BookMarkResponse> bookmarkMyBookmarksGet();

    // Category API

    // Auth API
    @POST(apiVersion + "auth/login")
    Call<AuthResponse> authLoginPost(@Body AuthRequest request);

    @POST(apiVersion + "auth/sign-up")
    Call<ResponseBody> authSignup(@Body SignUpRequest request);

    @GET(apiVersion + "auth/logout")
    Call<ResponseBody> authLogoutGet();

    // Mading API
    @GET(apiVersion + "madings")
    Call<MadingResponse> madingGet();

    @Multipart
    @POST(apiVersion + "madings")
    Call<ResponseBody> PostMading(@Part("Title") RequestBody title,
                                  @Part("Description") RequestBody description,
                                  @Part("MadingImage\"; filename=\"image.png") RequestBody fileImage);

    @PUT(apiVersion + "madings/{id}")
    Call<ResponseBody> madingPut(@Path("id") String id,
                                 @Body MadingRequest request);

    @GET(apiVersion + "madings/hot-topics")
    Call<MadingResponse> madingHotTopicsGet();

    @GET(apiVersion + "madings/my-madings")
    Call<MadingResponse> madingMyMadingsGet();
}
