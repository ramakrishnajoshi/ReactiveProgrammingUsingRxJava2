package com.example.reactiveprogrammingusingrxjava2.e_flatmap.repository;

import com.example.reactiveprogrammingusingrxjava2.e_flatmap.model.ApiComment;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.model.ApiPost;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.service.PostsBackend;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class UserPostsRepository {

    PostsBackend postsBackend;

    public UserPostsRepository() {
        this.postsBackend = PostsBackend.create();
    }

    public Observable<List<ApiPost>> getPosts() {
        return postsBackend
            .getPosts()
            /*.observeOn(Schedulers.io())*/;
    }

    public Observable<List<ApiComment>> getPostComments(int postid) {
        return postsBackend
            .getPostComments(postid);
    }

}
