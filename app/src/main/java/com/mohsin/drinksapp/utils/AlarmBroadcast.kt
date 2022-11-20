package com.mohsin.drinksapp.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.annotation.CallSuper
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import com.mohsin.drinksapp.R
import com.mohsin.drinksapp.data.local.DrinksDatabase
import com.mohsin.drinksapp.data.models.FavDrink
import com.mohsin.drinksapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
    }
}

@AndroidEntryPoint
class AlarmBrodcast : HiltBroadcastReceiver() {

    @Inject
    lateinit var db: DrinksDatabase

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        var favDrink: List<FavDrink>? = null

        CoroutineScope(Dispatchers.IO).launch {
            favDrink = db.favsDao().getAllDrinks().first()

        }

        //Click on Notification
        val intent1 = Intent(context, MainActivity::class.java)
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent =
            PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_IMMUTABLE)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(context, "notify_001")
        val contentView = RemoteViews(context.getPackageName(), R.layout.notification_layout)
        val pendingSwitchIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        if (favDrink != null) {
            contentView.setImageViewBitmap(
                R.id.drinkThumbnail,
                Utils.getImageFromInternalStorage(favDrink!![0].strDrinkThumb)
            )
            contentView.setTextViewText(R.id.drinkName, favDrink!![0].strDrink)
            contentView.setTextViewText(R.id.drinkInstructions, favDrink!![0].strInstructions)
            if (favDrink!![0].isFavorite) {
                contentView.setImageViewResource(R.id.favoriteIcon, R.drawable.ic_baseline_star_24)
            } else
                contentView.setImageViewResource(
                    R.id.favoriteIcon,
                    R.drawable.ic_baseline_star_border_unfilled_24
                )

            mBuilder.setSmallIcon(
                IconCompat.createWithBitmap(
                    Utils.getImageFromInternalStorage(
                        favDrink!![0].strDrinkThumb
                    )!!
                )
            )

        } else {

            contentView.setImageViewResource(R.id.drinkThumbnail, R.mipmap.ic_launcher)
            contentView.setTextViewText(R.id.drinkName, "")
            contentView.setTextViewText(R.id.drinkInstructions, "“Need some drinks open app now")

            mBuilder.setSmallIcon(R.mipmap.ic_launcher)
        }

        contentView.setOnClickPendingIntent(R.id.root, pendingSwitchIntent)

        mBuilder.setAutoCancel(true)
        mBuilder.setOngoing(true)
        mBuilder.priority = Notification.PRIORITY_HIGH
        mBuilder.setOnlyAlertOnce(true)
        mBuilder.build().flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH
        mBuilder.setContent(contentView)


        mBuilder.setContentIntent(pendingIntent)
        val channelId = "channel_id"
        val channel =
            NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH)
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)
        mBuilder.setChannelId(channelId)
        val notification: Notification = mBuilder.build()
        notificationManager.notify(1, notification)
    }
}