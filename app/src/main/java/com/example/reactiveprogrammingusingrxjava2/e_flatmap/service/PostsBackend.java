package com.example.reactiveprogrammingusingrxjava2.e_flatmap.service;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.example.reactiveprogrammingusingrxjava2.e_flatmap.model.*;

import java.util.List;

public interface PostsBackend {

    @GET("posts")
    Observable<List<ApiPost>> getPosts();

    @GET("posts/{postId}/comments")
    Observable<List<ApiComment>> getPostComments(@Path("postId") int postId);

    static PostsBackend create() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();

        Retrofit retrofit = new Retrofit
            .Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build();
        return retrofit.create(PostsBackend.class);
    }
}
