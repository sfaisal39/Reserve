package io.approots.reserve.Utilites;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.webkit.WebView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.approots.reserve.Activities.Home;
import io.approots.reserve.Activities.Home_Details;
import io.approots.reserve.Activities.MainActivity;
import io.approots.reserve.Activities.Splash_Screen;
import io.approots.reserve.R;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    //    int cont = 0;
    WebView myWebView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    NotificationManager notificationManager;
    Notification myNotification;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        //  if (remoteMessage.getData().size() > 0) {
//            Log.d("MESSSSSSSSSSSS", remoteMessage.getFrom());
//            Log.d("MESSSSSSSSSSSS", remoteMessage.getFrom());
        //  }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {


//            if (sharedpreferences.contains("NOTIFICATION")) {
//                cont = sharedpreferences.getInt("NOTIFICATION", 0);
//            }
//            cont++;
            String click_action = remoteMessage.getNotification().getClickAction();
            Log.v("DATAAAAAA", click_action + ":  AAAAA");
//            Log.d("MESSAGEAT:", remoteMessage.getData().;
//            Log.d("MESSSSSSSSSSSS", String.valueOf(cont));
//            remoteMessage.getNotification().
//            SharedPreferences.Editor editord = sharedpreferences.edit();
//            editord.putInt("NOTIFICATION", cont);
//            editord.commit();
//            editord.apply();
//            cont++;
//            remoteMessage.getNotification().
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
//            mostrarNotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    /**
     * Dispay the notification
     *
     * @param body
     */
    private void sendNotification(String body, String title) {


        Intent intent = new Intent(this, Notification.class);
        intent.putExtra("Click_Action", "OPEN_ACTIVITY_NAME");
        intent.setAction("OPEN_ACTIVITY_NAME");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "my_channel_01";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0/*Request code*/, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Set sound of notification
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher_notification);

        myNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setLargeIcon(rawBitmap)
                .setSmallIcon(R.drawable.ic_launcher_notification)
                .setGroupSummary(true)
                .setContentTitle(title)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setChannelId(CHANNEL_ID)
//                .setContent(Html.fromHtml(myHtml, new ImageGetter(), null)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .build();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            notificationManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(1 /*ID of notification*/, myNotification);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("CREATESERVICE", "Service destory");
    }


}