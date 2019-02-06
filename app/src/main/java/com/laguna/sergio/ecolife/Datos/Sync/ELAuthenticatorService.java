package com.laguna.sergio.ecolife.Datos.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ELAuthenticatorService extends Service {
    private ELAuthenticator elAuthenticator;
    @Override
    public void onCreate() {
        // Create a new authenticator object
        elAuthenticator = new ELAuthenticator(this);
    }
    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return elAuthenticator.getIBinder();
    }
}
