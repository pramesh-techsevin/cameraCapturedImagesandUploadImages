package com.example.cameracaptureandupload;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;


public class MyReceiver extends BroadcastReceiver {

    static int i = 0;

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    public void onReceive(Context context, Intent intent) {

//        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();

      /*  Log.d("API123", "" + intent.getAction());

        if (intent.getAction().equals("com.journaldev.broadcastreceiver.SOME_ACTION")){

            Broadcast.setText("Some Action Perform");

        Toast.makeText(context, "SOME_ACTION is received", Toast.LENGTH_LONG).show();

    }

       else if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                try {

                    Broadcast.setText("Internet Connected");
                    Toast.makeText(context, "Network is connected", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Broadcast.setText("No Internet");

                Toast.makeText(context, "Network is changed or reconnected", Toast.LENGTH_LONG).show();
            }
        }
       else if(intent.getAction().equals("")){}
*/


//        Notification(context, "Notification send");

       try {
           if (intent.getAction().equals("com.journaldev.broadcastreceiver.SOME_ACTION")) {

               sendNotification(context);
               Toast.makeText(context, "Alarm...."+i, Toast.LENGTH_LONG).show();
               i++;
               Log.d("check" ,"Total Alarm "+i);
           }
       }
       catch (Exception e){
           e.printStackTrace();
       }





    }

    public void sendNotification(Context context) {
//        idChannel = "my_channel_01";
        String body= "Keeping hydrated is crucial for health and well-being, but many people do not consume enough fluids each day.\n" +
                "Around 60 percent of the body is made up of water, and around 71 percent of the planet's surface is covered by water.\n" +
                "\n" +
                "Perhaps it is the ubiquitous nature of water that means drinking enough each day is not at the top of many people's lists of priorities.";

        try {

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            String channelId = "channel-01";
            String channelName = "Channel Name";
            int importance = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                importance = NotificationManager.IMPORTANCE_HIGH;
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(mChannel);
                }
            }
            Intent intent = new Intent(context, Broadcast.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.water)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.water))
                    .setTicker(context.getResources().getString(R.string.app_name))
                    .setDefaults(Notification.DEFAULT_VIBRATE)
//                    .setSound(notification)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setAutoCancel(true)
                    .setContentTitle("Water Notifications")
                    .setContentText(Html.fromHtml(body))
                    .setOngoing(false)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(Html.fromHtml(body)))
                    .setContentIntent(pendingIntent)
                    .setSubText("Tap to view the Application.")
                    ;
            if (notificationManager != null) {
                notificationManager.notify(new Random().nextInt(), mBuilder.build());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }








}

