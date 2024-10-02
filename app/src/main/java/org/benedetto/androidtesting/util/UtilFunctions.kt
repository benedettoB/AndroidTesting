package org.benedetto.androidtesting.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getCurrentWithMilis(): String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    val currentDate = Date()
    return dateFormat.format(currentDate)

}
private val threadName = Thread.currentThread().name
fun log(tag:String, msg:String){
    Log.d(tag, "Msg: $msg: Thread: $threadName, Time: ${getCurrentWithMilis()}")
}