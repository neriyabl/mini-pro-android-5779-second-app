package com.example.user.minipro5997secondapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.user.minipro5997secondapp.controller.MyReceiver;
import com.example.user.minipro5997secondapp.model.backend.BackendFactory;
import com.example.user.minipro5997secondapp.model.datasource.Firebase_DBManager;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;

import java.util.List;

public class MyService extends Service {

    private int lastCount = 0;
    Context context;
    Firebase_DBManager dbManager;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        dbManager = (Firebase_DBManager) BackendFactory.getBackend();
        context = getApplicationContext();
        dbManager.notifyToRequsetsList(new Firebase_DBManager.NotifyDataChange<List<ClientRequest>>() {
            @Override
            public void OnDataChanged(List<ClientRequest> obj) {
                try {
                    Intent intent = new Intent(context, MyReceiver.class);
                    sendBroadcast(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception exception) {
            }
        });
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
