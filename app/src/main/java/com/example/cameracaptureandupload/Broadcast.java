package com.example.cameracaptureandupload;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Broadcast extends Activity {
    MyReceiver receiver;
    IntentFilter intentFilter;
    static TextView msgtextView;
    EditText alarmtimeEditText;
    public Context mContext = this;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    String idChannel;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        msgtextView = findViewById(R.id.textView2);
        alarmtimeEditText = findViewById(R.id.alarmtimeEditText);

        receiver = new MyReceiver();
//        intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        //
//        startAlert();
        startAlarm();

    }

    public void onMessageReceived() {



        /*mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.drawable.camera);
        mBuilder.setContentTitle("Water Notification")
                .setContentText("Drink Water")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);


        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }*/
    }


    @Override
    protected void onResume() {
        super.onResume();

//        registerReceiver(receiver, intentFilter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


//        unregisterReceiver(receiver);

    }


    // broadcast a custom intent.

    public void broadcastIntent(View view) {

       /* Intent intent = new Intent();
        intent.setAction("com.journaldev.broadcastreceiver.SOME_ACTION");
        sendBroadcast(intent);*/
       /* CreateChannel createChannel = new CreateChannel();
        createChannel.sendNotification(this);*/


    }




    public static void setText(String val) {
        msgtextView.setText(val);
    }


    public void startAlarm() {
        final Handler h = new Handler();
        final int delay = 1000 * 120; //milliseconds

        h.postDelayed(new Runnable() {
            public void run() {
                //do something

                Intent alarmIntent = new Intent(getApplicationContext(), MyReceiver.class);
                alarmIntent.setAction("com.journaldev.broadcastreceiver.SOME_ACTION");
                sendBroadcast(alarmIntent);
                h.postDelayed(this, delay);
            }
        }, delay);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
