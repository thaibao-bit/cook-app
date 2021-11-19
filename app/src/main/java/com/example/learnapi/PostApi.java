package com.example.learnapi;
import com.example.learnapi.Model.Comment;
import com.example.learnapi.Model.Login;
import com.example.learnapi.Model.PostModel;
import com.example.learnapi.Model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostApi {


    String root = "https://cook-store.herokuapp.com/";
//    String root = "http://127.0.0.1:8000/";
    String root1 = "https://bao-api.herokuapp.com/";

    String base_local = root1 + "api/v1/";
    String BASE_URL = root + "api/";
    String API_URL = root + "api/";



    @POST("login/")
    Call<User> login(@Body Login login);

    @POST("register/")
    Call<User> registrationUser(@Body User userModel);


    @GET("videos/")
    Call<List<PostModel>> getListPost();

    @GET("comment/{id}")
    Call<List<Comment>> getCommentList(@Path(value = "id", encoded = true) String id);

    @GET("video/{id}/")
    Call<PostModel> getPost(@Path(value = "id", encoded = true) String id);

    @POST("postcomment/")
    Call<Comment> postComment(@Header("Authorization") String authToken, @Body Comment comment);
//
//    @POST("add/")
//    Call<PostModel> addPost(@Header("Authorization")  String authToken, @Body PostModel postModel);
//
//    @GET("profile/list/")
//    Call<List<PostModel>> getProfileList(@Header("Authorization")  String authToken);
//
//
//    @PUT("profile/edit/{id}/")
//    Call<PostModel> updatePost(@Header("Authorization")  String authToken, @Body PostModel postModelUpdate, @Path(value = "id", encoded = true) String id);
//
//
//    @DELETE("profile/delete/{id}/")
//    Call<List<PostModel>> deletePost(@Header("Authorization")  String authToken, @Path(value = "id", encoded = true) String id);
//
//
//    @GET("category/list/")
//    Call<List<CategoryModel>> getAllCategory();
//
//    @GET("list/{id}/")
//    Call<List<CategoryModel>> getCategoryById(@Path(value = "id", encoded = true) Integer id);

}