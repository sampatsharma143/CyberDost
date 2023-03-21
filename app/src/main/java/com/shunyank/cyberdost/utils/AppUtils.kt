package com.shunyank.cyberdost.utils

import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.shunyank.cyberdost.App
import io.appwrite.models.Account
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class AppUtils {


    companion object{
        val baseUrl = "https://shunaynk.in/avatars/"
        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS
        val pref = App.context.getSharedPreferences("CYBER_DOST_PREF",MODE_PRIVATE)

        fun getUser(): Account {
            return  Gson().fromJson( pref.getString("PREF_USER","").toString(),Account::class.java)
        }
        fun getUserString(): String {
            return  pref.getString("PREF_USER","").toString()
        }
        fun setUser(userData:String){
           pref.edit().putString("PREF_USER",userData).apply()
        }
        fun setUserProfile(userProfile:String){
            pref.edit().putString("PREF_USER_PROFILE",userProfile).apply()
        }
        fun getUserProfileString(): String {
            return  pref.getString("PREF_USER_PROFILE","").toString()
        }
        fun getUserHandle(email: String): String {
            return  email.substringBeforeLast("@")
        }
        fun getMalePics():List<String>{
            return listOf(
                baseUrl+"male_one.png",
                baseUrl+"male_two.png",
                baseUrl+"male_three.png",
                baseUrl+"male_forth.png",
                baseUrl+"male_fifth.png",
            )

        }

        fun getFemalePics():List<String>{
            return listOf(
                baseUrl+"female_one.png",
                baseUrl+"female_two.png",
                baseUrl+"female_three.png",
            )

        }
        fun getDate(oldDate: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = inputFormat.parse(oldDate)
            return outputFormat.format(date)
        }
        fun getAgoFormat(oldDate: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = inputFormat.parse(oldDate)
            return getTimeAgo(date)
        }
        private fun currentDate(): Date {
            val calendar = Calendar.getInstance()
            return calendar.time
        }

        fun getTimeAgo(date: Date): String {
            var time = date.time
            if (time < 1000000000000L) {
                time *= 1000
            }

            val now = currentDate().time
            if (time > now || time <= 0) {
                return "in the future"
            }

            val diff = now - time
            return  when {
                diff < MINUTE_MILLIS -> "moments ago"
                diff < 2 * MINUTE_MILLIS -> "a minute ago"
                diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
                diff < 2 * HOUR_MILLIS -> "an hour ago"
                diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
                diff < 48 * HOUR_MILLIS -> "yesterday"
                else -> "${diff / DAY_MILLIS} days ago"
            }
        }
    }

}