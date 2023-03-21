package com.shunyank.cyberdost.ui.home

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shunyank.cyberdost.models.PostModel
import com.shunyank.cyberdost.network.AppWriteHelper
import com.shunyank.cyberdost.network.callbacks.DocumentListFetchListener
import com.shunyank.cyberdost.network.callbacks.DocumentListFetchListenerForKotlin
import com.shunyank.cyberdost.network.utils.DatabaseUtils
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Document
import io.appwrite.services.Databases
import kotlin.Result


class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private  val _posts = MutableLiveData<ArrayList<PostModel>>().apply {
        emptyArray<PostModel>()
    }

    var posts :LiveData<ArrayList<PostModel>> = _posts

    suspend fun fetchPosts(activity:Activity){


       val database =  Databases(AppWriteHelper.getClient())

//        database.listDocuments("default","collection-post",listOf(Query.equal("status","ACTIVE")))

        DatabaseUtils.fetchDocumentsForKotlin(activity,"collection-post", listOf(Query.equal("status","ACTIVE"),Query.orderDesc("")),object:DocumentListFetchListenerForKotlin{
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

