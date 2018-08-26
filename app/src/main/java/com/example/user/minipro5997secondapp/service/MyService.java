package com.example.user.minipro5997secondapp.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.example.user.minipro5997secondapp.controller.MyReceiver;
import com.example.user.minipro5997secondapp.model.backend.BackendFactory;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    private int lastCount = 0;
    Context context;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        //ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        repeatingAlarmCreation();
        context = getApplicationContext();
        return START_STICKY;
       // return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void repeatingAlarmCreation() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(new Runnable() {
            @SuppressLint("StaticFieldLeak")
            public void run() {

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {

                        try {
                            ArrayList<ClientRequest> reservations = new ArrayList<>(BackendFactory.getBackend().getAllRequest());
                            if (lastCount < reservations.size()) {
                                lastCount = reservations.size();
                                Intent intent = new Intent(context, MyReceiver.class);
                                sendBroadcast(intent);

                            }} catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                    }
                }.execute();

            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
