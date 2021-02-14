package com.example.reactiveprogrammingusingrxjava2.e_flatmap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class ApiPost {
    int userId;
    @SerializedName("id")
    int postId;
    String title;
    String body;

    List<ApiComment> commentList;

    public ApiPost(int userId, int postId, String title, String body, List<ApiComment> commentList) {
        this.userId = userId;
        this.postId = postId;
        this.title = title;
        this.body = body;
        this.commentList = commentList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<ApiComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ApiComment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "ApiPost{" +
            "userId=" + userId +
            ", postId=" + postId +
            ", title='" + title + '\'' +
            ", body='" + body + '\'' +
            ", commentList=" + commentList +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiPost apiPost = (ApiPost) o;
        return userId == apiPost.userId &&
            postId == apiPost.postId &&
            Objects.equals(title, apiPost.title) &&
            Objects.equals(body, apiPost.body) &&
            Objects.equals(commentList, apiPost.commentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId, title, body, commentList);
    }
}
