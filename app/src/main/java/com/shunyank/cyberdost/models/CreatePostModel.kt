package com.shunyank.cyberdost.models

import com.google.gson.annotations.SerializedName

data class CreatePostModel(
    val post_content:String,
    val categories:String,
    val user_id :String,
    val status:String,
    val users_name:String,
    val profile_url:String,
    val user_email:String,
    val user_handle:String
)
