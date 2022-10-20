package com.example.notificationapp.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.notificationapp.MainActivity;
import com.example.notificationapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingServices extends FirebaseMessagingService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
        }
        sendNotification(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(String from, String body) {


        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("notification",body);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setContentTitle("Unilever")
                        .setContentText(body)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        String refreshToken= FirebaseMessaging.getInstance().getToken().toString();

        sendRegistrationToServer(refreshToken);
    }

    private void sendRegistrationToServer(String refreshToken) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Users").setValue(refreshToken);
    }
}
