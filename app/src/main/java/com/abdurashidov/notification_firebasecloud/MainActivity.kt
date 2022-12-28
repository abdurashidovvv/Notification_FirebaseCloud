package com.abdurashidov.notification_firebasecloud

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.abdurashidov.notification_firebasecloud.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private  val TAG = "MainActivity"

    var max=100

    companion object{
        const val channelId="1"
        const val notificationId=1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Android versiyasi 8.0 dan kichik qurilmalar uchun
     /*   binding.btn.setOnClickListener {
            Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
            val builder=NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Title")
                .setContentText("Text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notification=builder.build()
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId, notification)
        }*/

        //Android versiyasi 8.0 va undan yuqori versiya uchun

        binding.btn.setOnClickListener {

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val intent=Intent(this, MainActivity::class.java)

                val deleteIntent=Intent(this, MyService::class.java)
                deleteIntent.setAction("ru.startandroid.notifications.action_delete")
                val pendingIntent=PendingIntent.getActivity(this, 0, deleteIntent, PendingIntent.FLAG_IMMUTABLE)




                val builder=NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title")
                    .setContentText("Much longer text that cannot fit one line...")
                    //.setProgress(max, 0, true) //Download notificationlarda ishlaydi
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setContentIntent(pendingIntent)    //Notification bosilganida MainActivity ochiladi
                    .addAction(android.R.drawable.ic_delete, "Delete", pendingIntent)



                //Create Notification channel
                val name = getString(R.string.channel_name)
                val descriptionText = getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(channelId, name, importance).apply {
                    description = descriptionText
                }

                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notification=builder.build()

                notificationManager.createNotificationChannel(channel)
                notificationManager.notify(notificationId, notification)



//                Thread {
//                    try {
//                        TimeUnit.SECONDS.sleep(3)
//                    } catch (e: InterruptedException) {
//                        e.printStackTrace()
//                    }
//                    var progress = 0
//                    while (progress < max) {
//                        try {
//                            TimeUnit.MILLISECONDS.sleep(300)
//                        } catch (e: InterruptedException) {
//                            e.printStackTrace()
//                        }
//                        progress += 10
//
//                        // show notification with current progress
//                        builder.setProgress(max, progress, false)
//                            .setContentText("$progress of $max")
//                        notificationManager.notify(1, builder.build())
//                    }
//
//                    // show notification without progressbar
//                    builder.setProgress(0, 10, false)
//                        .setContentText("Completed")
//                    notificationManager.notify(1, builder.build())
//                }.start()
            }


        }


        binding.btn1.setOnClickListener {
            val notificationManager:NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)
        }
    }
}