package com.mohsin.drinksapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.*
import java.net.URL
import java.util.*


object Utils {

    fun downloadAndSaveImage(imageStr: String, drinkId: String, context: Context): String {

        val url: URL = URL(imageStr)
        val `in`: InputStream = BufferedInputStream(url.openStream())
        val out = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var n: Int
        while (-1 != `in`.read(buf).also { n = it }) {
            out.write(buf, 0, n)
        }
        out.close()
        `in`.close()
        val response = out.toByteArray()

        var file: File? = null
        file = File(context.filesDir, drinkId)
        file.createNewFile()
        val fos = FileOutputStream(file)
        fos.write(response)
        fos.flush()
        fos.close()

        return file.path
    }

    fun getImageFromInternalStorage(filePath: String): Bitmap? {
        return BitmapFactory.decodeFile(filePath)
    }

    fun schedulePushNotifications(context: Context) {
        try {
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val alarmPendingIntent by lazy {
                val intent = Intent(context, AlarmBrodcast::class.java)
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }

            val calendar = GregorianCalendar.getInstance().apply {

                if (get(Calendar.HOUR_OF_DAY) >= 18) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }

                set(Calendar.HOUR_OF_DAY, 18)
                set(Calendar.MINUTE, 55)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmPendingIntent
            )
        } catch (e: Exception) {
            Log.d(TAG, "schedulePushNotifications: $e")
        }
    }
}