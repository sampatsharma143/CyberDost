package com.shunyank.cyberdost.models;

import com.google.gson.annotations.SerializedName;

public class PostModel {
    /*
    *     val post_content:String,
    val categories:String,
    val user_id :String,
    val status:String,
    val users_name:String,
    val profile_url:String,
    val user_email:String,
    val user_handle:String
    *
    * */
    @SerializedName("$id")
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String post_content;
    String categories;
    String status;
    String users_name;
    String profile_url;
    String user_handle;
    Long   likes_count;
    Long   comments_count;
    Long   victims_count;
    boolean isAlreadyLiked;

    public boolean isAlreadyLiked() {
        return isAlreadyLiked;
    }

    public void setAlreadyLiked(boolean alreadyLiked) {
        isAlreadyLiked = alreadyLiked;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsers_name() {
        return users_name;
    }

    public void setUsers_name(String users_name) {
        this.users_name = users_name;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getUser_handle() {
        return user_handle;
    }

    public void setUser_handle(String user_handle) {
        this.user_handle = user_handle;
    }

    public Long getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(Long likes_count) {
        this.likes_count = likes_count;
    }

    public Long getComments_count() {
        return comments_count;
    }

    public void setComments_count(Long comments_count) {
        this.comments_count = comments_count;
    }

    public Long getVictims_count() {
        return victims_count;
    }

    public void setVictims_count(Long victims_count) {
        this.victims_count = victims_count;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    @SerializedName("$createdAt")
    String create_at;

}
