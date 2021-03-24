package com.retroportalstudio.www.background_service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MyService extends Service {
    private IBinder mBinder = new MyBinder();
    Handler handler;
    Runnable runnable;
    int i = 1;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

//        int badgeCount = 1;
//        ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+
////        ShortcutBadger.with(getApplicationContext()).count(badgeCount);

        String id = "my_channel_01";
        CharSequence name = "channel_name";
        String description = "channel_description";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        mChannel.setShowBadge(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(MyService.this.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(mChannel);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MyService.this,id)
                    .setContentText("This is running in Background i = "+i)
                    .setContentTitle("Flutter Background")
                    .setSmallIcon(R.drawable.ic_android_black_24dp)
                    .setNumber(5)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
            startForeground(102,builder.build());
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyService.this,id)
                            .setContentText("This is running in Background i = "+i)
                            .setContentTitle("N Sterie")
                            .setSmallIcon(R.drawable.ic_android_black_24dp)
                            .setNumber(5)
                            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
                    startForeground(101,builder.build());
                }
                i+=1;
                Log.d("ttest", "runnable i = "+i);
                handler.postDelayed(runnable, 1000);
            }
        };
        Log.d("ttest", "runnable s");
        handler.postDelayed(runnable, 1000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }
}
