package com.example.user.minipro5997secondapp.model.backend;

import com.example.user.minipro5997secondapp.model.datasource.Firebase_DBManager;

public class BackendFactory {
    private static Backend backend = null;

    public static Backend getBackend() {
        if (backend == null)
            backend = new Firebase_DBManager();
        return backend;
    }
}
