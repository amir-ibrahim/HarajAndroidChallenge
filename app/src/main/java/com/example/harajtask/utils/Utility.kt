package com.example.harajtask.utils

import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import com.example.harajtask.utils.Constants.PLAY_STORE_URL_PREFIX
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

//object Utility {
//}

fun convertDate(dateInMilliseconds: String, dateFormat: String?): String? {
//    "dd/MM/yyyy hh:mm:ss"
    return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
}

/**
 * Time Ago
 **/
fun timeAgo(dateInMilliseconds: Long): String? {
    val oldDateInMilliseconds: Long = (dateInMilliseconds.toString() + "000").toLong()
    val currentTimestamp = System.currentTimeMillis()
    val diffInMillisec: Long = currentTimestamp - oldDateInMilliseconds

    val diffInDays: Long = TimeUnit.MILLISECONDS.toDays(diffInMillisec)
    val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMillisec)
    val diffInMin: Long = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec)
//    val diffInSec: Long = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec)

    if (diffInDays < 1 && diffInHours < 1 && diffInMin < 1) {
        Timber.d("DateMinutes: Just Now")
        return "Just Now"
    } else if (diffInDays < 1 && diffInHours < 1 && diffInMin > 0) {
        Timber.d("DateMinutes: $diffInMin minutes ago")
        return "$diffInMin Minutes Ago"
    } else if (diffInDays < 1 && diffInHours > 0) {
        Timber.d("DateMinutes: $diffInHours hours ago")
        return "$diffInHours Hours Ago"
    } else if (diffInDays in 1..6) {
        Timber.d("DateMinutes: $diffInDays days ago")
        return "$diffInDays Days Ago"
    } else if (diffInDays in 7..29) {
        Timber.d("DateMinutes: " + diffInDays / 7 + " week ago")
        return "${diffInDays / 7} + Week Ago"
    } else if (diffInDays in 30..364) {
        Timber.d("DateMinutes: " + diffInDays / 30 + " month ago")
        return "${diffInDays / 30} Month Ago"
    } else if (diffInDays > 364) {
        Timber.d("DateMinutes: " + diffInDays / 365 + " year ago")
        return "${diffInDays / 365} years Ago"
    }
    return "$diffInDays Days Ago"
}
// end

/**
 * Share App
 **/
fun shareTheApp(context: Context, subject: String, message: String) {
    try {
        val appUrl = PLAY_STORE_URL_PREFIX + context.packageName
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, subject)
        var leadingText = "\n" + message + "\n\n"
        leadingText += appUrl + "\n\n"
        i.putExtra(Intent.EXTRA_TEXT, leadingText)
        context.startActivity(Intent.createChooser(i, "Share using"))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
// end