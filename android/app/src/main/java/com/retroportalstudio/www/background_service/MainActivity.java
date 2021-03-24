package com.retroportalstudio.www.background_service;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import me.leolin.shortcutbadger.ShortcutBadger;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.FlutterEngine;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import android.content.Context;

import androidx.core.app.NotificationCompat;

public class MainActivity extends FlutterActivity {

  private Intent forService;
  boolean mServiceBound = true;
  MyService mBoundService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    forService = new Intent(MainActivity.this,MyService.class);

    int badgeCount = 1;
    ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

    new MethodChannel(getFlutterView(),"com.retroportalstudio.messages")
            .setMethodCallHandler(new MethodChannel.MethodCallHandler() {
      @Override
      public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if(methodCall.method.equals("startService")){
          startService(forService);
          bindService(forService, mServiceConnection, Context.BIND_AUTO_CREATE);
//          startService();
          result.success("Service Started");

        }
      }
    });


  }

//  @Override
//  protected void onDestroy() {
//    super.onDestroy();
//    stopService(forService);
//  }

//  private void startService(){
//    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//      startForegroundService(forService);
//    } else {
//      startService(forService);
//    }
//  }

  private ServiceConnection mServiceConnection = new ServiceConnection() {

    @Override
    public void onServiceDisconnected(ComponentName name) {
      mServiceBound = false;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      MyService.MyBinder myBinder = (MyService.MyBinder) service;
      mBoundService = myBinder.getService();
      mServiceBound = true;
    }
  };

}
