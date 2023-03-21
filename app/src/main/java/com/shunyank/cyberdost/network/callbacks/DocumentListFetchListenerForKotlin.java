package com.shunyank.cyberdost.network.callbacks;

import java.util.List;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Document;
import kotlin.Result;

public interface DocumentListFetchListenerForKotlin {
    void onFetchSuccessfully(List<Document> documents);
    void onFailed();
    void onException(AppwriteException exception);
}
