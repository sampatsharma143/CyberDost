package com.shunyank.cyberdost.network.callbacks;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Document;
import kotlin.Result;

public interface DocumentUpdateListenerForK {
    void onUpdatedSuccessfully(Document document);
    void onFailed();
    void onException(AppwriteException exception);
}
