package com.shunyank.cyberdost.network;

import androidx.fragment.app.FragmentActivity;

import com.shunyank.cyberdost.models.PostModel;
import com.shunyank.cyberdost.network.callbacks.DocumentListFetchListener;
import com.shunyank.cyberdost.network.utils.DatabaseUtils;
import com.shunyank.cyberdost.utils.AppUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.appwrite.Query;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Document;
import kotlin.Result;

public class Constants {
    public static String AppUrl = "backend.live";
    public static String API_VERSION = "/v1";
    public static String AppWrite_AppName = "https://app.";
    public static String EventCollection="event_collection";


    public static String getAppUrl() {
        return AppWrite_AppName+AppUrl+API_VERSION;
    }

    public static String NodeApiEndPoint = "https://megamind-nodejs-backend-test.herokuapp.com";
    public static String NodeApiVersion = "/api/v1/";

    public static String UserCollectionId = "users-collection";
    public static String databaseId = "default";


}
