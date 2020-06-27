package com.example.meditation.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.meditation.R
import com.example.meditation.view.main.MainActivity

class NotificationHelper(var context: Context) {

    private val notificationId = NOTIFICATION_ID
    private var manager: NotificationManager? = null

    init {
        registerNotificationChannel()
    }

    private fun registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Chennel_1"
            val descriptionText = "アプリがバックグラウンドに行った際に通知を表示します。"
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }


    fun startNotification(){

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, RQ_PENDING_INTENT, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.meiso_logo))
            .setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        val notification = builder.build()
        notification.flags = Notification.DEFAULT_LIGHTS or Notification.FLAG_AUTO_CANCEL
        manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager?.notify(notificationId, notification)


    }

    fun cancelNotification(){
        manager?.cancel(notificationId)

    }


}