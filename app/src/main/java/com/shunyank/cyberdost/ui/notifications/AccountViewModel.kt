package com.shunyank.cyberdost.ui.notifications

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shunyank.cyberdost.models.PostModel
import com.shunyank.cyberdost.network.AppWriteHelper
import com.shunyank.cyberdost.network.callbacks.DocumentListFetchListenerForKotlin
import com.shunyank.cyberdost.network.utils.DatabaseUtils
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Document
import io.appwrite.services.Databases

class AccountViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private  val _posts = MutableLiveData<ArrayList<PostModel>>().apply {
        emptyArray<PostModel>()
    }
    var posts :LiveData<ArrayList<PostModel>> = _posts

    suspend fun fetchMyPosts(activity: Activity,userId:String){


        val database =  Databases(AppWriteHelper.getClient())

//        database.listDocuments("default","collection-post",listOf())

        DatabaseUtils.fetchDocumentsForKotlin(activity,"collection-post", listOf(Query.equal("status","ACTIVE"),Query.equal("user_id",userId)),object:
            DocumentListFetchListenerForKotlin {
            override fun onFetchSuccessfully(documents: MutableList<Document>?) {
                _posts.value = DatabaseUtils.convertToModelList(documents,PostModel::class.java)
            }

            override fun onFailed() {
            }

            override fun onException(exception: AppwriteException?) {
            }

        })
    }

}