package com.laguna.sergio.ecolife.Datos.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class EcoLifeSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static EcoLifeSyncAdapter EcoSyncAdapter = null;
    @Override
    public void onCreate() {
        Log.d("EcoLifeSyncService", "onCreate - EcoLifeSyncService");
        synchronized (sSyncAdapterLock) {
            if (EcoSyncAdapter == null) {
                EcoSyncAdapter = new EcoLifeSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return EcoSyncAdapter.getSyncAdapterBinder();
    }
}
