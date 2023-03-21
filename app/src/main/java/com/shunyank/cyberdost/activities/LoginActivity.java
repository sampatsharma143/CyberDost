package com.shunyank.cyberdost.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import com.shunyank.cyberdost.MainActivity;
import com.shunyank.cyberdost.databinding.ActivityLoginBinding;
import com.shunyank.cyberdost.network.AppWriteHelper;
import com.shunyank.cyberdost.network.callbacks.DocumentListFetchListener;
import com.shunyank.cyberdost.network.utils.DatabaseUtils;
import com.shunyank.cyberdost.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import io.appwrite.Query;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Document;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start login process

                Account account = new Account(AppWriteHelper.getClient());
                try {
                    account.createOAuth2Session(
                            LoginActivity.this,
                            "google",
                            new Continuation<Unit>() {
                                @NonNull
                                @Override
                                public CoroutineContext getContext() {
                                    return EmptyCoroutineContext.INSTANCE;
                                }
                                @Override
                                public void resumeWith(@NonNull Object o) {
                                    if(o instanceof Result.Failure){
                                        Log.e("login","login error");
                                    }else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                    saveAccount();
                                                Log.e("login","login success");                                        }
                                        });
                                    }
                                }
                            }
                    );
                } catch (AppwriteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void saveAccount() {

        try {
            // TODO: 19/03/23 show a dialog with message loggin in
            dialog.setMessage("Please wait..");
            dialog.show();
            getAccount();
        } catch (AppwriteException e) {

            Toast.makeText(this, "Something Went Wrong!\nCould not get account details", Toast.LENGTH_SHORT).show();

//            throw new RuntimeException(e);

        }

    }

    private void checkIsNewAccount(String userId){
        List<String> queries = new ArrayList<>();
        queries.add(Query.Companion.equal("user_id",userId));
        DatabaseUtils.fetchDocuments(LoginActivity.this, "collection-user", queries, new DocumentListFetchListener() {
            @Override
            public void onFetchSuccessfully(List<Document> documents) {
                if(documents.size()>0){


                    AppUtils.Companion.setUserProfile(documents.get(0).getData().get("user_profile").toString());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Intent intent = new Intent(LoginActivity.this, SelectProfileActivity.class);
                    intent.putExtra("user_id",userId);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onFailed(Result.Failure failure) {

            }

            @Override
            public void onException(AppwriteException exception) {
                    exception.printStackTrace();
            }
        });
    }
    private void getAccount() throws AppwriteException {
        Account account = new Account(AppWriteHelper.getClient());
        account.get(new Continuation<io.appwrite.models.Account>() {
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NonNull Object o) {
                if(o instanceof Result.Failure){
                    Log.e("failed","Login");
                    Toast.makeText(LoginActivity.this, "Something Went Wrong!\nCould not get account details", Toast.LENGTH_SHORT).show();

                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            io.appwrite.models.Account account1 = (io.appwrite.models.Account) o;
                            String id = account1.getId();
                            String email = account1.getEmail();
                            String name = account1.getName();

                            AppUtils.Companion.setUser(new Gson().toJson(account1).toString());
                            checkIsNewAccount(id);


                        }
                    });

                    Log.e("account result",new Gson().toJson(o));
                }
            }
        });
    }
}