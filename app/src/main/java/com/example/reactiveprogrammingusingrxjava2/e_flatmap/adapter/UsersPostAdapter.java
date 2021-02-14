package com.example.reactiveprogrammingusingrxjava2.e_flatmap.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.reactiveprogrammingusingrxjava2.R;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.model.ApiPost;

import java.util.ArrayList;
import java.util.List;

/*public class UsersPostAdapter extends ListAdapter<ApiPost, UsersPostAdapter.UsersPostViewHolder> {

    public UsersPostAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public UsersPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.row_post_detail, parent, false);
        return new UsersPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersPostViewHolder holder, int position) {
        ApiPost apiPost = getItem(position);
        holder.postId.setText(String.valueOf(apiPost.getPostId()));
        holder.postTitle.setText(apiPost.getTitle());
        if(apiPost.getCommentList() != null && !apiPost.getCommentList().isEmpty()) {
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.commentsCount.setText(String.valueOf(apiPost.getCommentList().size()));
        } else {
            holder.progressBar.setVisibility(View.GONE);
        }
    }

    public void updatePost(ApiPost apiPost ) {
        getCurrentList().set(getCurrentList().indexOf(apiPost), apiPost);
        notifyItemChanged(getCurrentList().indexOf(apiPost));
    }

    public class UsersPostViewHolder extends RecyclerView.ViewHolder {

        TextView postId, postTitle, commentsCount;
        ProgressBar progressBar;

        public UsersPostViewHolder(@NonNull View itemView) {
            super(itemView);
            postId = itemView.findViewById(R.id.post_id);
            postTitle = itemView.findViewById(R.id.post_title);
            commentsCount = itemView.findViewById(R.id.comments_count);
            progressBar = itemView.findViewById(R.id.comments_count_progressbar);
        }
    }

    public static final DiffUtil.ItemCallback<ApiPost> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<ApiPost>() {
            @Override
            public boolean areItemsTheSame(
                @NonNull ApiPost oldApiPost, @NonNull ApiPost newApiPost) {
                // User properties may have changed if reloaded from the DB, but ID is fixed
                return oldApiPost.getPostId() == newApiPost.getPostId();
            }
            @Override
            public boolean areContentsTheSame(
                @NonNull ApiPost oldApiPost, @NonNull ApiPost newApiPost) {
                // NOTE: if you use equals, your object must properly override Object#equals()
                // Incorrectly returning false here will result in too many animations.
                return oldApiPost.equals(newApiPost);
            }
        };
}*/


public class UsersPostAdapter extends RecyclerView.Adapter<UsersPostAdapter.UsersPostViewHolder> {

    ArrayList<ApiPost> apiPostList = new ArrayList<ApiPost>();

    public void setApiPostList(ArrayList<ApiPost> apiPostList) {
        this.apiPostList = apiPostList;
    }

    @NonNull
    @Override
    public UsersPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.row_post_detail, parent, false);
        return new UsersPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersPostViewHolder holder, int position) {
        ApiPost apiPost = apiPostList.get(position);
        holder.postId.setText(String.valueOf(apiPost.getPostId()));
        holder.postTitle.setText(apiPost.getTitle());
        if(apiPost.getCommentList() != null && !apiPost.getCommentList().isEmpty()) {
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.commentsCount.setText(String.valueOf(apiPost.getCommentList().size()));
        } else {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.commentsCount.setText("");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick", "" + apiPost.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiPostList.size();
    }

    public void updatePost(ApiPost apiPost ) {
        apiPostList.set(apiPostList.indexOf(apiPost), apiPost);
        notifyItemChanged(apiPostList.indexOf(apiPost));
    }

    public class UsersPostViewHolder extends RecyclerView.ViewHolder {

        TextView postId, postTitle, commentsCount;
        ProgressBar progressBar;

        public UsersPostViewHolder(@NonNull View itemView) {
            super(itemView);
            postId = itemView.findViewById(R.id.post_id);
            postTitle = itemView.findViewById(R.id.post_title);
            commentsCount = itemView.findViewById(R.id.comments_count);
            progressBar = itemView.findViewById(R.id.comments_count_progressbar);
        }
    }
}
