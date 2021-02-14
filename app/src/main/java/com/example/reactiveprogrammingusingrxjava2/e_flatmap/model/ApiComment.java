package com.example.reactiveprogrammingusingrxjava2.e_flatmap.model;

import com.google.gson.annotations.SerializedName;

public class ApiComment {
    int postId;
    @SerializedName("id")
    int commentId;
    String name;
    String email;
    String body;

    public ApiComment(int postId, int commentId, String name, String email, String body) {
        this.postId = postId;
        this.commentId = commentId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ApiComment{" +
            "postId=" + postId +
            ", commentId=" + commentId +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", body='" + body + '\'' +
            '}';
    }
}
