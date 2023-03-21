package com.shunyank.cyberdost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import com.shunyank.cyberdost.activities.LoginActivity
import com.shunyank.cyberdost.activities.LoginKotlinActivity
import com.shunyank.cyberdost.activities.SelectProfileActivity
import com.shunyank.cyberdost.utils.AppUtils

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

      object:  CountDownTimer(2000, 1000){
          override fun onTick(p0: Long) {

          }

          override fun onFinish() {
              runOnUiThread {

                  val user = AppUtils.getUserString()
                  var intent : Intent? = null
                   if(user.isEmpty()){
                       intent =  Intent(this@SplashActivity, LoginActivity::class.java)

                  }else {

                      if(AppUtils.getUserProfileString().isEmpty()){
                          intent =   Intent(this@SplashActivity, SelectProfileActivity::class.java)
                          intent.putExtra("user_id",AppUtils.getUser().id)


                      }else {


                          intent =   Intent(this@SplashActivity, MainActivity::class.java)

                      }
                  }
                  startActivity(intent)

                  finish()
              }

          }
      }.start()


    }}