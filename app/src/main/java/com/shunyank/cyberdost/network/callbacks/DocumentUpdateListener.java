package com.shunyank.cyberdost.network.callbacks;

import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Document;
import kotlin.Result;

public interface DocumentUpdateListener {
    void onUpdatedSuccessfully(Document document);
    void onFailed(Result.Failure failure);
    void onException(AppwriteException exception);
}
