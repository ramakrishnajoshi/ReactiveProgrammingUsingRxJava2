package com.example.reactiveprogrammingusingrxjava2.e_flatmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.reactiveprogrammingusingrxjava2.R;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.adapter.UsersPostAdapter;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.model.ApiComment;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.model.ApiPost;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.viewmodel.UserPostsViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserPostsActivity extends AppCompatActivity {

    String TAG = "UserPostsActivity";
    int commentAPICallsCounter = 0;

    RecyclerView usersPostsRecyclerview;
    UsersPostAdapter usersPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        usersPostsRecyclerview = findViewById(R.id.recyclerview_user_posts);

        setRecyclerview();

        UserPostsViewModel viewModel = ViewModelProviders.of(this).get(UserPostsViewModel.class);

        ArrayList<ApiPost> apiPostList = new ArrayList();

        viewModel.postsLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                apiPostList.addAll((List<ApiPost>)o);
                Log.e(TAG, "apiPostList response: " + apiPostList.toString());
                Log.e(TAG, "apiPostList response size: " + apiPostList.size());
                usersPostAdapter.submitList(apiPostList);
            }
        });

        viewModel.commentLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                List<ApiComment> apiCommentList = (List<ApiComment>)o;
                Log.e(TAG, "apiCommentList response: " + apiCommentList.toString());
                commentAPICallsCounter++;
                Log.e(TAG, "commentAPICallsCounter : " + commentAPICallsCounter);
                if(apiCommentList.isEmpty()){

                } else {

                }
            }
        });

        viewModel.getPostsList();
    }

    private void setRecyclerview() {
        usersPostsRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        usersPostAdapter = new UsersPostAdapter();
        usersPostsRecyclerview.setAdapter(usersPostAdapter);
    }
}
