package com.example.user.minipro5997secondapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
