package com.example.reactiveprogrammingusingrxjava2.e_flatmap.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reactiveprogrammingusingrxjava2.R;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.model.ApiPost;

public class UsersPostAdapter extends ListAdapter<ApiPost, UsersPostAdapter.UsersPostViewHolder> {

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
        holder.postId.setText(apiPost.getPostId());
        holder.postTitle.setText(apiPost.getTitle());
        if(apiPost.getCommentList() != null && !apiPost.getCommentList().isEmpty()) {
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.commentsCount.setText(apiPost.getCommentList().size());
        } else {
            holder.progressBar.setVisibility(View.GONE);
        }
    }

    public class UsersPostViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView postId, postTitle, commentsCount, progressBar;

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
}
