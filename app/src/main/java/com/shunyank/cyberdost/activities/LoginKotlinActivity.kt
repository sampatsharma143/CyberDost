package com.shunyank.cyberdost.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.shunyank.cyberdost.R
import com.shunyank.cyberdost.network.AppWriteHelper
import io.appwrite.services.Account
import kotlinx.coroutines.launch

class LoginKotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_kotlin)

        findViewById<ConstraintLayout>(R.id.login_button).setOnClickListener{
            val account = Account(AppWriteHelper.getClient())


            lifecycleScope.launch {
                account.createOAuth2Session(this@LoginKotlinActivity, "google").also {
                    android.util.Log.e("also","true")
                }
            }
        }



    }
}