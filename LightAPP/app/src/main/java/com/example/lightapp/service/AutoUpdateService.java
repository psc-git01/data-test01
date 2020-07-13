package com.example.lightapp.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.lightapp.LightActivity;
import com.example.lightapp.LoginActivity;
import com.example.lightapp.data.Light;

public class AutoUpdateService extends Service {
    private LightActivity lightActivity = new LightActivity();
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int clazzId = intent.getIntExtra("class_id",0);
        Log.d("aaaaa","aa"+clazzId+"aa");
        lightActivity.requestLight(clazzId);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int s = 2*1000;//定时2秒
        long triggerAtTime = SystemClock.elapsedRealtime()+s;
        Intent i = new Intent(this,AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);

        return super.onStartCommand(intent, flags, startId);
    }


}
